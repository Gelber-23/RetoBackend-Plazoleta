package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.api.IOrderServicePort;
import com.course.plazoleta.domain.api.IUtilsServicePort;
import com.course.plazoleta.domain.exception.ClientHaveOrderActiveException;
import com.course.plazoleta.domain.exception.OrderValidationException;
import com.course.plazoleta.domain.exception.RestaurantValidationException;
import com.course.plazoleta.domain.model.Order;
import com.course.plazoleta.domain.model.OrderDish;
import com.course.plazoleta.domain.spi.IOrderPersistencePort;
import com.course.plazoleta.domain.utils.constants.DtoConstants;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IUtilsServicePort utilsServicePort;



    private static final List<String> ACTIVE_STATES_ORDER = Arrays.asList(
            ValuesConstants.STATUS_PENDING_ORDER,
            ValuesConstants.STATUS_PREPARATION_ORDER,
            ValuesConstants.STATUS_READY_ORDER
    );
    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IUtilsServicePort utilsServicePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.utilsServicePort = utilsServicePort;
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
}
