package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.exception.NoDataFoundException;
import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.infraestructure.output.jpa.entity.DishEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IDishEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.IDishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishJpaAdapterTest {


    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @InjectMocks
    private DishJpaAdapter dishJpaAdapter;

    @Test
    void saveDish_shouldCallRepositorySaveWithMappedEntity() {
        Dish dish = new Dish();
        DishEntity entity = new DishEntity();
        when(dishEntityMapper.toEntity(dish)).thenReturn(entity);

        dishJpaAdapter.saveDish(dish);

        verify(dishRepository).save(entity);
    }

    @Test
    void getDishById_shouldReturnMappedModel() {
        Long id = 1L;
        DishEntity entity = new DishEntity();
        Dish model = new Dish();

        when(dishRepository.findById(id)).thenReturn(Optional.of(entity));
        when(dishEntityMapper.toModel(entity)).thenReturn(model);

        Dish result = dishJpaAdapter.getDishById(id);

        verify(dishRepository).findById(id);
        verify(dishEntityMapper).toModel(entity);
        assertEquals(model, result);
    }

    @Test
    void getDishById_shouldThrowExceptionWhenNotFound() {
        Long id = 1L;
        when(dishRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoDataFoundException.class, () -> dishJpaAdapter.getDishById(id));
    }

    @Test
    void getAllDishes_shouldReturnMappedList() {
        DishEntity entity = new DishEntity();
        Dish model = new Dish();
        List<DishEntity> entityList = Collections.singletonList(entity);
        List<Dish> modelList = Collections.singletonList(model);

        when(dishRepository.findAll()).thenReturn(entityList);
        when(dishEntityMapper.toModelList(entityList)).thenReturn(modelList);

        List<Dish> result = dishJpaAdapter.getAllDishes();

        verify(dishRepository).findAll();
        verify(dishEntityMapper).toModelList(entityList);
        assertEquals(modelList, result);
    }

    @Test
    void getAllDishes_shouldThrowExceptionWhenEmpty() {
        when(dishRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataFoundException.class, () -> dishJpaAdapter.getAllDishes());
    }

    @Test
    void updateDish_shouldCallSaveWithMappedEntity() {
        Dish dish = new Dish();
        DishEntity entity = new DishEntity();
        when(dishEntityMapper.toEntity(dish)).thenReturn(entity);

        dishJpaAdapter.updateDish(dish);

        verify(dishRepository).save(entity);
    }

    @Test
    void changeStateDish_shouldCallSaveWithMappedEntity() {
        Dish dish = new Dish();
        DishEntity entity = new DishEntity();
        when(dishEntityMapper.toEntity(dish)).thenReturn(entity);

        dishJpaAdapter.changeStateDish(dish);

        verify(dishRepository).save(entity);
    }

}