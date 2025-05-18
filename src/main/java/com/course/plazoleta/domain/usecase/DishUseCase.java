package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.api.IDishServicePort;
import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.spi.IDishPersistencePort;

import java.util.List;

public class DishUseCase implements IDishServicePort{

    private final IDishPersistencePort dishPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public void saveDish(Dish dish) {
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public Dish getDishById(Long id) {
        return dishPersistencePort.getDishById(id);
    }

    @Override
    public List<Dish> getAllDishes() {
        return dishPersistencePort.getAllDishes();
    }
}
