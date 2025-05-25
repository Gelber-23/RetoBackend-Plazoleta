package com.course.plazoleta.infraestructure.output.jpa.repository;

import com.course.plazoleta.infraestructure.output.jpa.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDishRepository  extends JpaRepository<OrderDishEntity, Long> {
    List<OrderDishEntity> findAllByIdOrder_IdIn(List<Long> orderIds);
    List<OrderDishEntity> findAllByIdOrder_Id(Long orderId);
}
