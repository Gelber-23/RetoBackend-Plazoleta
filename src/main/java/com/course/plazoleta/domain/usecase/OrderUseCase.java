package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.api.IOrderServicePort;
import com.course.plazoleta.domain.api.IUserServicePort;
import com.course.plazoleta.domain.api.IUtilsServicePort;
import com.course.plazoleta.domain.exception.*;
import com.course.plazoleta.domain.model.*;
import com.course.plazoleta.domain.spi.IOrderPersistencePort;
import com.course.plazoleta.domain.utils.constants.DtoConstants;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;

import java.util.*;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IUtilsServicePort utilsServicePort;
    private final IUserServicePort userServicePort;


    private static final List<String> ACTIVE_STATES_ORDER = Arrays.asList(
            ValuesConstants.STATUS_PENDING_ORDER,
            ValuesConstants.STATUS_PREPARATION_ORDER,
            ValuesConstants.STATUS_READY_ORDER
    );
    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IUtilsServicePort utilsServicePort, IUserServicePort userServicePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.utilsServicePort = utilsServicePort;
        this.userServicePort = userServicePort;
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
        Order order = orderPersistencePort.getOrderById(idOrder);
        if (order == null){
            throw new NoDataFoundException();

        }
        if (!Objects.equals(order.getState(), ValuesConstants.STATUS_PENDING_ORDER)){
            throw new NoDataFoundException();

        }
        order.setState(ValuesConstants.STATUS_PREPARATION_ORDER);
        order.setIdChef(user.getId());

        return orderPersistencePort.takeOrder( order );

    }

    public void validateOrder(Order order){
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

    public User validEmployeeUser(){
        long idUser = utilsServicePort.getCurrentUserId();
        User user = userServicePort.getUserById(idUser);

        if(user.getRol() != null && user.getRol().getId() != ValuesConstants.ID_ROLE_EMPLOYEE)
            throw new NotEmployeeUserException();

        if(user.getIdRestaurant() != null && user.getIdRestaurant() <= 0)
            throw new UserIsNotEmployeeRestaurantException();

        return user;
    }
}
