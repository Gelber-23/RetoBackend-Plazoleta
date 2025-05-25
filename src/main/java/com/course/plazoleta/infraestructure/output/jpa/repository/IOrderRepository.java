package com.course.plazoleta.infraestructure.output.jpa.repository;

import com.course.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByIdClientAndStateIn(Long idClient, List<String> states);
    Page<OrderEntity> findAllByStateAndIdRestaurant_Id(String state, Long restaurantId, Pageable pageable);
}
