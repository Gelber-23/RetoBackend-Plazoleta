package com.course.plazoleta.infraestructure.output.jpa.repository;

import com.course.plazoleta.infraestructure.output.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    Page<DishEntity> findByIdRestaurant_IdAndIdCategory_Id(Long idRestaurant, Long idCategory, Pageable pageable);
}
