package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.api.IOrderServicePort;
import com.course.plazoleta.domain.api.IUserServicePort;
import com.course.plazoleta.domain.api.IUtilsServicePort;
import com.course.plazoleta.domain.exception.*;
import com.course.plazoleta.domain.model.*;
import com.course.plazoleta.domain.spi.IOrderPersistencePort;
import com.course.plazoleta.domain.spi.ITwilioClientPort;
import com.course.plazoleta.domain.utils.constants.DtoConstants;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;

import java.security.SecureRandom;
import java.util.*;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IUtilsServicePort utilsServicePort;
    private final IUserServicePort userServicePort;
    private final ITwilioClientPort twilioClientPort;

    private static final SecureRandom random = new SecureRandom();

    private static final List<String> ACTIVE_STATES_ORDER = Arrays.asList(
            ValuesConstants.STATUS_PENDING_ORDER,
            ValuesConstants.STATUS_PREPARATION_ORDER,
            ValuesConstants.STATUS_READY_ORDER
    );
    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IUtilsServicePort utilsServicePort, IUserServicePort userServicePort, ITwilioClientPort twilioClientPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.utilsServicePort = utilsServicePort;
        this.userServicePort = userServicePort;
        this.twilioClientPort = twilioClientPort;
    }

    @Override
    public void createOrder(Order order) {

        validateOrder(order);
        order.setState(ValuesConstants.STATUS_PENDING_ORDER);
        order.setDate(new Date());
        order.setIdClient(utilsServicePort.getCurrentUserId());
        order.setIdChef(0L);


        boolean existActiveOrder = orderPersistencePort.clientHaveOrderActive(order.getIdClient() , ACTIVE_STATES_ORDER);

        if( existActiveOrder){
            throw new ClientHaveOrderActiveException();
        }



        orderPersistencePort.createOrder(order);
    }

    @Override
    public PageModel<Order> getOrdersFilterByState(Integer page, Integer pageSize, String state) {

        User user = validEmployeeUser();

        return orderPersistencePort.getOrdersFilterByState(page,pageSize,state,user.getIdRestaurant());
    }

    @Override
    public Order takeOrder(Long idOrder) {
        User user = validEmployeeUser();
        Order order =validateExistOrder(idOrder);
        if (!Objects.equals(order.getState(), ValuesConstants.STATUS_PENDING_ORDER)){
            throw new NoDataFoundException();

        }
        order.setState(ValuesConstants.STATUS_PREPARATION_ORDER);
        order.setIdChef(user.getId());

        return orderPersistencePort.takeOrder( order );

    }

    @Override
    public Order completeOrderAndNotify(Long idOrder) {
        validEmployeeUser();
        Order order = validateExistOrder(idOrder);
        if (!Objects.equals(order.getState(), ValuesConstants.STATUS_PREPARATION_ORDER)){
            throw new NoDataFoundException();

        }
        order.setState(ValuesConstants.STATUS_READY_ORDER);
        order.setPin(generatePin());

        User user = userServicePort.getUserById(order.getIdClient());
        if (user == null){
            throw new NoDataFoundException();

        }
        String message = generateMessage(user.getName() , order.getPin()) ;

        String phone  = user.getPhone();

        order = orderPersistencePort.takeOrder(order);

        MessageSms messageSms = new MessageSms(phone,message);
        twilioClientPort.sendMessageSms(messageSms);

        return order;
    }

    @Override
    public Order deliverOrder(Long idOrder, String pin) {
        validEmployeeUser();
        Order order = validateExistOrder(idOrder);
        if (!Objects.equals(order.getState(), ValuesConstants.STATUS_READY_ORDER)){
            throw new NoDataFoundException();

        }
        if(!Objects.equals(order.getPin(), pin)) {
            throw new PinNotMatchException();
        }
        order.setState(ValuesConstants.STATUS_DELIVERED_ORDER);


        return orderPersistencePort.takeOrder(order);
    }

    private void validateOrder(Order order){
        List<String> errors = new ArrayList<>();
        if (order.getDishes() == null || order.getDishes().isEmpty()) {
            errors.add(DtoConstants.MUST_BE_ONE_DISH);
        } else {
            for (OrderDish dish : order.getDishes()) {
                if (dish.getIdDish() == null || dish.getIdDish() <= 0) {
                    errors.add("Dish:" + DtoConstants.FIELD_NOT_NEGATIVE_MESSAGE);

                }
                if (dish.getQuantity()  <= 0) {
                    errors.add("Dish Quantity:" + DtoConstants.FIELD_NOT_NEGATIVE_MESSAGE);

                }
            }

        }

        if (order.getIdRestaurant() == null || order.getIdRestaurant() <= 0) {
            errors.add("Restaurant:" + DtoConstants.FIELD_NOT_NEGATIVE_MESSAGE);

        }

        if (!errors.isEmpty()) {
            throw new OrderValidationException(errors);
        }
    }
    private User validEmployeeUser(){
        long idUser = utilsServicePort.getCurrentUserId();
        User user = userServicePort.getUserById(idUser);

        if(user.getRol() != null && user.getRol().getId() != ValuesConstants.ID_ROLE_EMPLOYEE)
            throw new NotEmployeeUserException();

        if(user.getIdRestaurant() != null && user.getIdRestaurant() <= 0)
            throw new UserIsNotEmployeeRestaurantException();

        return user;
    }
    private Order validateExistOrder(Long idOrder){
        Order order = orderPersistencePort.getOrderById(idOrder);
        if (order == null || order.getId()<= 0){
            throw new NoDataFoundException();

        }
        return order;
    }
    public static String generateMessage(String client, String pin) {
        return String.format(ValuesConstants.MESSAGE_ORDER_READY, client.toUpperCase(), pin);
    }

    private  String generatePin() {
        StringBuilder pin = new StringBuilder(ValuesConstants.PIN_LENGTH);
        for (int i = 0; i < ValuesConstants.PIN_LENGTH; i++) {
            int index = random.nextInt(ValuesConstants.ALPHABET_CHARACTERS_PIN.length());
            pin.append(ValuesConstants.ALPHABET_CHARACTERS_PIN.charAt(index));
        }
        return pin.toString();
    }
}
