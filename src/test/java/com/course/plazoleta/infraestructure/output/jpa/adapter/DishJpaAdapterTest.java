package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.exception.NoDataFoundException;
import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.infraestructure.output.jpa.entity.DishEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IDishEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.IDishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
    @Test
    void getAllDishesByRestaurantByCategory_shouldReturnPageModel() {
        int page = 0;
        int pageSize = 2;
        int idRestaurant = 1;
        int idCategory = 2;

        DishEntity dishEntity = new DishEntity();
        Dish dish = new Dish();
        List<DishEntity> dishEntities = Collections.singletonList(dishEntity);
        List<Dish> dishes = Collections.singletonList(dish);

        Page<DishEntity> pageResult = new PageImpl<>(dishEntities, PageRequest.of(page, pageSize), dishEntities.size());

        when(dishRepository.findByIdRestaurant_IdAndIdCategory_Id(
                (long) idRestaurant,
                (long) idCategory,
                PageRequest.of(page, pageSize)
        )).thenReturn(pageResult);

        when(dishEntityMapper.toModel(dishEntity)).thenReturn(dish);

        PageModel<Dish> result = dishJpaAdapter.getAllDishesByRestaurantByCategory(page, pageSize, idRestaurant, idCategory);

        assertEquals(dishes, result.getContent());
        assertEquals(page, result.getPage());
        assertEquals(pageSize, result.getSize());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());

        verify(dishRepository).findByIdRestaurant_IdAndIdCategory_Id((long) idRestaurant, (long) idCategory, PageRequest.of(page, pageSize));
        verify(dishEntityMapper).toModel(dishEntity);
    }
}