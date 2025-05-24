package com.course.plazoleta.domain.api;
import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.model.PageModel;

import java.util.List;

public interface IDishServicePort {
    void saveDish(Dish dish);

    Dish getDishById(Long  id);

    List<Dish> getAllDishes();

    PageModel<Dish> getAllDishesByRestaurantByCategory(Integer page , Integer pageSize, Integer idRestaurant,Integer idCategory);

    void  updateDish(Dish dish);

    void  changeStateDish(Dish id);

}
