package com.course.plazoleta.infraestructure.output.jpa.repository;

import com.course.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByIdClientAndStateIn(Long idClient, List<String> states);
}
