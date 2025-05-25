package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.exception.DishNotFoundException;
import com.course.plazoleta.domain.exception.NoDataFoundException;
import com.course.plazoleta.domain.model.Order;
import com.course.plazoleta.domain.model.OrderDish;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.spi.IOrderPersistencePort;
import com.course.plazoleta.infraestructure.output.jpa.entity.DishEntity;
import com.course.plazoleta.infraestructure.output.jpa.entity.OrderDishEntity;
import com.course.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IOrderDishEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IOrderEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.IDishRepository;
import com.course.plazoleta.infraestructure.output.jpa.repository.IOrderDishRepository;
import com.course.plazoleta.infraestructure.output.jpa.repository.IOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IDishRepository dishRepository;
    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;

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
    @Transactional()
    public PageModel<Order> getOrdersFilterByState(Integer page, Integer pageSize, String state , Long idRestaurant) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "id"));

        Page<OrderEntity> orderEntityPage = orderRepository.findAllByStateAndIdRestaurant_Id(state, idRestaurant,pageable);


        List<Order> orderList = orderEntityPage.getContent()
                .stream()
                .map(orderEntityMapper::toOrderModel)
                .toList();

        List<Long> orderIds = orderList.stream()
                .map(Order::getId)
                .toList();

        if (!orderIds.isEmpty()) {

            List<OrderDishEntity> dishEntities =
                    orderDishRepository.findAllByIdOrder_IdIn(orderIds);


            Map<Long, List<OrderDish>> dishesByOrder = dishEntities.stream()
                    .map(orderDishEntityMapper::toOrderModel)
                    .collect(Collectors.groupingBy(OrderDish::getIdOrder));


            orderList.forEach(o ->
                    o.setDishes(dishesByOrder.getOrDefault(o.getId(), Collections.emptyList()))
            );
        }
        return new PageModel<>(
                orderList,
                orderEntityPage.getNumber(),
                orderEntityPage.getSize(),
                orderEntityPage.getTotalElements(),
                orderEntityPage.getTotalPages()
        );
    }

    @Override
    public boolean clientHaveOrderActive(Long idClient,List<String> states) {

        return orderRepository.existsByIdClientAndStateIn(idClient, states);

    }

    @Override
    public Order getOrderById(Long idOrder) {
        return orderEntityMapper.toOrderModel(orderRepository.findById(idOrder)
                .orElseThrow(NoDataFoundException::new));
    }

    @Override
    public Order takeOrder(Order order) {
        OrderEntity orderEntity = orderRepository.save(orderEntityMapper.toEntity(order));
        Order orderResponse = orderEntityMapper.toOrderModel(orderEntity) ;

        List<OrderDishEntity> dishEntities = orderDishRepository.findAllByIdOrder_Id(orderEntity.getId());
        List<OrderDish> dishes = dishEntities.stream()
                .map(orderDishEntityMapper::toOrderModel)
                .toList();
        orderResponse.setDishes(dishes);

        return orderResponse;
    }
}
