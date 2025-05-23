package com.course.plazoleta.application.handler;

import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.request.DishUpdateRequest;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.domain.model.Dish;

import java.util.List;

public interface IDishHandler {
    void saveDish(DishRequest dishRequest);

    DishResponse getDishById(Long id);

    List<DishResponse> getAllDishes();

    void  updateDish(DishUpdateRequest dish);

    void  changeStateDish(Dish id);
}
