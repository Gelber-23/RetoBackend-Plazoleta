package com.course.plazoleta.domain.spi;

import com.course.plazoleta.domain.model.Dish;

import java.util.List;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    Dish getDishById(Long id);

    List<Dish> getAllDishes();

}
