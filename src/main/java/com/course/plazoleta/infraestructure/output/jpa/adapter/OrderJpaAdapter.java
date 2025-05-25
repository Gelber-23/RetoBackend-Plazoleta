package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.exception.DishNotFoundException;
import com.course.plazoleta.domain.model.Order;
import com.course.plazoleta.domain.model.OrderDish;
import com.course.plazoleta.domain.spi.IOrderPersistencePort;
import com.course.plazoleta.infraestructure.output.jpa.entity.DishEntity;
import com.course.plazoleta.infraestructure.output.jpa.entity.OrderDishEntity;
import com.course.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IOrderEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.IDishRepository;
import com.course.plazoleta.infraestructure.output.jpa.repository.IOrderDishRepository;
import com.course.plazoleta.infraestructure.output.jpa.repository.IOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IDishRepository dishRepository;
    private final IOrderDishRepository orderDishRepository;

    @Override
    @Transactional
    public void createOrder(Order order) {
        OrderEntity orderEntity = orderRepository.save(orderEntityMapper.toEntity(order));

        List<OrderDishEntity> orderDishEntityList = new ArrayList<>();

        for(OrderDish orderDish : order.getDishes()){
            OrderDishEntity orderDishEntity = new OrderDishEntity();
            Optional<DishEntity> dish = dishRepository.findById(orderDish.getIdDish());
            if( dish.isEmpty())
                throw new DishNotFoundException();

            orderDishEntity.setIdDish( dish.get() );
            orderDishEntity.setIdOrder(orderEntity);
            orderDishEntity.setQuantity(orderDish.getQuantity());

            orderDishEntityList.add(orderDishEntity);
        }

        orderDishRepository.saveAll(orderDishEntityList);


    }

    @Override
    public boolean clientHaveOrderActive(Long idClient,List<String> states) {

        return orderRepository.existsByIdClientAndStateIn(idClient, states);

    }
}
