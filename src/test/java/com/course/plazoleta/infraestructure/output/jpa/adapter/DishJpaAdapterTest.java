package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.exception.NoDataFoundException;
import com.course.plazoleta.infraestructure.output.jpa.entity.CategoryEntity;
import com.course.plazoleta.infraestructure.output.jpa.entity.DishEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IDishEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.IDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishJpaAdapterTest {

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @InjectMocks
    private DishJpaAdapter adapter;

    private Dish domainDish;
    private DishEntity entity;

    @BeforeEach
    void setUp() {
        // Domain model
        domainDish = new Dish();
        domainDish.setId(1L);
        domainDish.setName("Taco");
        domainDish.setDescription("Spicy");
        domainDish.setIdCategory(2L);
        domainDish.setPrice(100);
        domainDish.setUrlImage("url");
        domainDish.setIdRestaurant(3);
        domainDish.setActive(false);


        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(2L);
        categoryEntity.setName("Mexican");
        categoryEntity.setDescription("Mexican cuisine");


        entity = new DishEntity();
        entity.setId(1L);
        entity.setName("Taco");
        entity.setDescription("Spicy");
        entity.setIdCategory(categoryEntity); // setter for CategoryEntity field
        entity.setPrice(100);
        entity.setUrlImage("url");
        entity.setIdRestaurant(3);
        entity.setActive(true);
    }

    @Test
    void saveDish_setsActiveAndCallsRepository() {
        when(dishEntityMapper.toEntity(domainDish)).thenReturn(entity);

        adapter.saveDish(domainDish);

        assertTrue(domainDish.getActive());
        verify(dishEntityMapper).toEntity(domainDish);
        verify(dishRepository).save(entity);
    }

    @Test
    void getDishById_returnsMappedModel() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(dishEntityMapper.toModel(entity)).thenReturn(domainDish);

        Dish result = adapter.getDishById(1L);

        assertEquals(domainDish, result);
        verify(dishRepository).findById(1L);
        verify(dishEntityMapper).toModel(entity);
    }

    @Test
    void getDishById_throwsWhenNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoDataFoundException.class, () -> adapter.getDishById(1L));
        verify(dishRepository).findById(1L);
        verify(dishEntityMapper, never()).toModel(any());
    }

    @Test
    void getAllDishes_returnsMappedList() {
        List<DishEntity> entities = Collections.singletonList(entity);
        List<Dish> domains = Collections.singletonList(domainDish);

        when(dishRepository.findAll()).thenReturn(entities);
        when(dishEntityMapper.toModelList(entities)).thenReturn(domains);

        List<Dish> result = adapter.getAllDishes();

        assertEquals(domains, result);
        verify(dishRepository).findAll();
        verify(dishEntityMapper).toModelList(entities);
    }

    @Test
    void getAllDishes_throwsWhenEmpty() {
        when(dishRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataFoundException.class, () -> adapter.getAllDishes());
        verify(dishRepository).findAll();
        verify(dishEntityMapper, never()).toModelList(anyList());
    }

    @Test
    void updateDish_updatesFieldsAndSaves() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(entity));

        domainDish.setPrice(150);
        domainDish.setDescription("Very spicy");

        adapter.updateDish(domainDish);

        assertEquals(150, entity.getPrice());
        assertEquals("Very spicy", entity.getDescription());
        verify(dishRepository).findById(1L);
        verify(dishRepository).save(entity);
    }

    @Test
    void updateDish_throwsWhenNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> adapter.updateDish(domainDish));
        assertTrue(ex.getMessage().contains("Dish not found: 1"));

        verify(dishRepository).findById(1L);
        verify(dishRepository, never()).save(any());
    }
}