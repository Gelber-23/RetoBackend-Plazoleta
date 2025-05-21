package com.course.plazoleta.domain.spi;

import com.course.plazoleta.domain.model.Dish;

import java.util.List;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    Dish getDishById(long id);

    List<Dish> getAllDishes();
    void  updateDish(Dish dish);
}
