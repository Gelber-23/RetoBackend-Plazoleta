package com.course.plazoleta.infraestructure.output.jpa.repository;

import com.course.plazoleta.infraestructure.output.jpa.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDishRepository  extends JpaRepository<OrderDishEntity, Long> {
}
