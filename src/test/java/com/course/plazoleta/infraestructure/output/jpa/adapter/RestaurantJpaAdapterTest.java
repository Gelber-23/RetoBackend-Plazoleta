package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.model.Restaurant;
import com.course.plazoleta.infraestructure.exception.NoDataFoundException;
import com.course.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.IRestaurantRepository;
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
class RestaurantJpaAdapterTest {
    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    @Test
    void saveRestaurant_shouldCallRepositoryWithMappedEntity() {
        Restaurant restaurant = new Restaurant();
        RestaurantEntity entity = new RestaurantEntity();

        when(restaurantEntityMapper.toEntity(restaurant)).thenReturn(entity);

        restaurantJpaAdapter.saveRestaurant(restaurant);

        verify(restaurantRepository).save(entity);
    }

    @Test
    void getRestaurantById_shouldReturnMappedRestaurant() {
        long id = 1;
        RestaurantEntity entity = new RestaurantEntity();
        Restaurant model = new Restaurant();

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(entity));
        when(restaurantEntityMapper.toModel(entity)).thenReturn(model);

        Restaurant result = restaurantJpaAdapter.getRestaurantById(id);

        assertEquals(model, result);
    }

    @Test
    void getRestaurantById_shouldThrowWhenNotFound() {
        long id = 99;
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoDataFoundException.class, () -> restaurantJpaAdapter.getRestaurantById(id));
    }

    @Test
    void getAllRestaurants_shouldReturnMappedList() {
        List<RestaurantEntity> entities = Collections.singletonList(new RestaurantEntity());
        List<Restaurant> models =Collections.singletonList(new Restaurant());

        when(restaurantRepository.findAll()).thenReturn(entities);
        when(restaurantEntityMapper.toModelList(entities)).thenReturn(models);

        List<Restaurant> result = restaurantJpaAdapter.getAllRestaurants();

        assertEquals(models, result);
    }

    @Test
    void getAllRestaurants_shouldThrowWhenEmpty() {
        when(restaurantRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataFoundException.class, () -> restaurantJpaAdapter.getAllRestaurants());
    }

    @Test
    void deleteRestaurantById_shouldCallRepository() {
        long id = 10;
        restaurantJpaAdapter.deleteRestaurantById(id);

        verify(restaurantRepository).deleteById(id);
    }


}