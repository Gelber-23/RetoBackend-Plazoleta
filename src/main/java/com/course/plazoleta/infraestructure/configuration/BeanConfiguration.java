package com.course.plazoleta.infraestructure.configuration;

import com.course.plazoleta.domain.api.ICategoryServicePort;
import com.course.plazoleta.domain.api.IDishServicePort;
import com.course.plazoleta.domain.api.IOrderServicePort;
import com.course.plazoleta.domain.api.IRestaurantServicePort;
import com.course.plazoleta.domain.spi.ICategoryPersistencePort;
import com.course.plazoleta.domain.spi.IDishPersistencePort;
import com.course.plazoleta.domain.spi.IOrderPersistencePort;
import com.course.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.course.plazoleta.domain.usecase.CategoryUseCase;
import com.course.plazoleta.domain.usecase.DishUseCase;
import com.course.plazoleta.domain.usecase.OrderUseCase;
import com.course.plazoleta.domain.usecase.RestaurantUseCase;
import com.course.plazoleta.infraestructure.output.feing.UserClientFeignAdapter;
import com.course.plazoleta.infraestructure.output.jpa.adapter.CategoryJpaAdapter;
import com.course.plazoleta.infraestructure.output.jpa.adapter.DishJpaAdapter;
import com.course.plazoleta.infraestructure.output.jpa.adapter.OrderJpaAdapter;
import com.course.plazoleta.infraestructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.course.plazoleta.infraestructure.output.jpa.mapper.ICategoryEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IDishEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IOrderEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.course.plazoleta.infraestructure.output.jpa.repository.*;
import com.course.plazoleta.infraestructure.utils.UtilsAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final UserClientFeignAdapter userClientFeignAdapter;
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishRepository orderDishRepository;
    private final UtilsAdapter utilsAdapter;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort(),userClientFeignAdapter);
    }

    @Bean
    public IDishPersistencePort dishPersistencePort(){
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort(){
        return new DishUseCase(dishPersistencePort(),restaurantPersistencePort(),utilsAdapter);
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(){
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderJpaAdapter(orderRepository, orderEntityMapper,dishRepository,orderDishRepository);
    }

    @Bean
    public IOrderServicePort orderServicePort(){
        return new OrderUseCase(orderPersistencePort() , utilsAdapter);
    }

}
