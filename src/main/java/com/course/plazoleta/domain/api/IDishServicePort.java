package com.course.plazoleta.domain.api;



import com.course.plazoleta.domain.model.Dish;

import java.util.List;

public interface IDishServicePort {
    void saveDish(Dish dish);

    Dish getDishById(Long id);

    List<Dish> getAllDishes();

    void  updateDish(Dish dish);

}
