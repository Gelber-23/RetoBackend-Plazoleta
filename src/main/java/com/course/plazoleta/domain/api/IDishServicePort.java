package com.course.plazoleta.domain.api;



import com.course.plazoleta.domain.model.Dish;

import java.util.List;

public interface IDishServicePort {
    void saveDish(Dish dish);

    Dish getDishById(long id);

    List<Dish> getAllDishes();

    void  updateDish(Dish dish);

   // void  updateStateDish(long id);

}
