package com.course.plazoleta.infraestructure.output.jpa.adapter;

import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.spi.IDishPersistencePort;
import com.course.plazoleta.domain.exception.NoDataFoundException;
import com.course.plazoleta.infraestructure.output.jpa.entity.DishEntity;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IDishEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class DishJpaAdapter  implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private  final IDishEntityMapper dishEntityMapper;

    @Override
    public void saveDish(Dish dish) {
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }

    @Override
    public Dish getDishById(Long id) {
        return dishEntityMapper.toModel(dishRepository.findById(id)
                .orElseThrow(NoDataFoundException::new));
    }

    @Override
    public PageModel<Dish> getAllDishesByRestaurantByCategory(Integer page, Integer pageSize, Integer idRestaurant, Integer idCategory) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<DishEntity> pageResult = dishRepository.findByIdRestaurant_IdAndIdCategory_IdAndActiveTrue(
                idRestaurant.longValue(),
                idCategory.longValue(),
                pageable
        );

        List<Dish> dishes = pageResult.getContent()
                .stream()
                .map(dishEntityMapper::toModel)
                .toList();

        return new PageModel<>(
                dishes,
                page,
                pageSize,
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Override
    public List<Dish> getAllDishes() {
        List<DishEntity> dishEntityList = dishRepository.findAll();
        if (dishEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return dishEntityMapper.toModelList(dishEntityList);
    }

    @Override
    public void updateDish(Dish dish) {
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }

    @Override
    public void changeStateDish(Dish dish) {
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }
}
