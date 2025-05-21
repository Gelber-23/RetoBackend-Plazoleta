package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.model.Restaurant;
import com.course.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.course.plazoleta.infraestructure.exception.NoDataFoundException;
import com.course.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {
    private final IRestaurantRepository restaurantRepository;
    private  final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
    }

    @Override
    public Restaurant getRestaurantById(long id) {
        return restaurantEntityMapper.toModel(restaurantRepository.findById(id)
                .orElseThrow(NoDataFoundException::new));
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<RestaurantEntity> restaurantEntityList = restaurantRepository.findAll();
        if (restaurantEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return restaurantEntityMapper.toModelList(restaurantEntityList);
    }

    @Override
    public void deleteRestaurantById(long id) {
        restaurantRepository.deleteById(id);
    }
}
