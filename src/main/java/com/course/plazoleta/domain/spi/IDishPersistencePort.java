package com.course.plazoleta.domain.spi;

import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.model.PageModel;

import java.util.List;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    Dish getDishById(Long id);
    PageModel<Dish> getAllDishesByRestaurantByCategory(Integer page , Integer pageSize, Integer idRestaurant, Integer idCategory);

    List<Dish> getAllDishes();
    void  updateDish(Dish dish);
    void  changeStateDish(Dish dish);
}
