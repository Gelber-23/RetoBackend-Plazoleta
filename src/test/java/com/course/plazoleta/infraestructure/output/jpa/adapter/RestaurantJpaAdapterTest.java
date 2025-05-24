package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.exception.NoDataFoundException;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.model.Restaurant;
import com.course.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantJpaAdapterTest {

    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @Test
    void saveRestaurant_shouldCallRepositoryWithMappedEntity() {
        Restaurant restaurant = new Restaurant();
        RestaurantEntity entity = new RestaurantEntity();
        when(restaurantEntityMapper.toEntity(restaurant)).thenReturn(entity);

        restaurantJpaAdapter.saveRestaurant(restaurant);

        verify(restaurantRepository).save(entity);
    }

    @Test
    void getRestaurantById_shouldReturnMappedModel() {
        long id = 1L;
        RestaurantEntity entity = new RestaurantEntity();
        Restaurant expectedModel = new Restaurant();

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(entity));
        when(restaurantEntityMapper.toModel(entity)).thenReturn(expectedModel);

        Restaurant result = restaurantJpaAdapter.getRestaurantById(id);

        verify(restaurantRepository).findById(id);
        verify(restaurantEntityMapper).toModel(entity);
        assertEquals(expectedModel, result);
    }

    @Test
    void getRestaurantById_shouldThrowExceptionWhenNotFound() {
        long id = 1L;
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoDataFoundException.class, () -> restaurantJpaAdapter.getRestaurantById(id));
    }

    @Test
    void getAllRestaurants_shouldReturnPageModelMapped() {
        int page = 0;
        int size = 5;
        String field = "name";

        RestaurantEntity entity = new RestaurantEntity();
        Restaurant model = new Restaurant();
        List<RestaurantEntity> entityList = Collections.singletonList(entity);
        List<Restaurant> modelList = Collections.singletonList(model);
        Page<RestaurantEntity> pageEntity = new PageImpl<>(entityList, PageRequest.of(page, size), 1);

        when(restaurantRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, field))))
                .thenReturn(pageEntity);
        when(restaurantEntityMapper.toModel(entity)).thenReturn(model);

        PageModel<Restaurant> result = restaurantJpaAdapter.getAllRestaurants(page, size, field);

        assertEquals(modelList, result.getContent());
        assertEquals(page, result.getPage());
        assertEquals(size, result.getSize());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void deleteRestaurantById_shouldCallRepositoryDelete() {
        long id = 1L;
        restaurantJpaAdapter.deleteRestaurantById(id);
        verify(restaurantRepository).deleteById(id);
    }
  
}