package com.course.plazoleta.application.handler.impl;

import com.course.plazoleta.application.dto.request.DishRequest;
import com.course.plazoleta.application.dto.request.DishUpdateRequest;
import com.course.plazoleta.application.dto.response.DishResponse;
import com.course.plazoleta.application.handler.IDishHandler;
import com.course.plazoleta.application.mapper.request.IDishRequestMapper;
import com.course.plazoleta.application.mapper.request.IDishUpdateRequestMapper;
import com.course.plazoleta.application.mapper.response.IDishResponseMapper;
import com.course.plazoleta.domain.api.IDishServicePort;
import com.course.plazoleta.domain.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {
    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishUpdateRequestMapper dishUpdateRequestMapper;
    private final IDishResponseMapper dishResponseMapper;


    @Override
    public void saveDish(DishRequest dishRequest) {
        Dish dish = dishRequestMapper.toDish(dishRequest);
        dishServicePort.saveDish(dish);
    }

    @Override
    public DishResponse getDishById(long id) {
        Dish dish = dishServicePort.getDishById(id);
        return dishResponseMapper.toResponse(dish);
    }

    @Override
    public List<DishResponse> getAllDishes() {
        return dishResponseMapper.toResponseList(dishServicePort.getAllDishes());
    }

    @Override
    public void updateDish(DishUpdateRequest dish) {
        dishServicePort.updateDish(dishUpdateRequestMapper.toDish(dish));
    }


}
