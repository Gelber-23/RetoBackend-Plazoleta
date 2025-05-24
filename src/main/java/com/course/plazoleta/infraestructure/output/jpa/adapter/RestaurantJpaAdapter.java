package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.model.Restaurant;
import com.course.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.course.plazoleta.domain.exception.NoDataFoundException;
import com.course.plazoleta.infraestructure.output.jpa.entity.RestaurantEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


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
    public PageModel<Restaurant> getAllRestaurants(Integer page , Integer pageSize, String fieldToSort) {

        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, fieldToSort));

        Page<RestaurantEntity> pg = restaurantRepository.findAll(pageable);

        return new PageModel<>(
                pg.getContent().stream().map(restaurantEntityMapper::toModel).toList(),
                pg.getNumber(),
                pg.getSize(),
                pg.getTotalElements(),
                pg.getTotalPages()
        );

    }

    @Override
    public void deleteRestaurantById(long id) {
        restaurantRepository.deleteById(id);
    }
}
