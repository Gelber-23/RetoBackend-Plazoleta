package com.course.plazoleta.application.handler;

import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.request.DishUpdateRequest;
import com.course.plazoleta.application.dto.response.DishListResponse;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.model.PageModel;

import java.util.List;

public interface IDishHandler {
    void saveDish(DishRequest dishRequest);

    DishResponse getDishById(Long id);

    List<DishResponse> getAllDishes();
    PageModel<DishListResponse> getAllDishesByRestaurantByCategory(Integer page , Integer pageSize, Integer idRestaurant, Integer idCategory);

    void  updateDish(DishUpdateRequest dish);

    void  changeStateDish(Dish id);
}
