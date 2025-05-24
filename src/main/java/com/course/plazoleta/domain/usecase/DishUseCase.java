package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.api.IDishServicePort;
import com.course.plazoleta.domain.api.IUtilsServicePort;
import com.course.plazoleta.domain.exception.DishValidationException;
import com.course.plazoleta.domain.exception.NoDataFoundException;
import com.course.plazoleta.domain.exception.UserIsNotOwnerValidationException;
import com.course.plazoleta.domain.model.Dish;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.model.Restaurant;
import com.course.plazoleta.domain.spi.IDishPersistencePort;
import com.course.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;

import java.util.ArrayList;
import java.util.List;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUtilsServicePort utilsServicePort;


    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IUtilsServicePort utilsServicePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.utilsServicePort = utilsServicePort;
    }

    @Override
    public void saveDish(Dish dish) {
        validateDish(dish);
        validateUserIsOwnerRestaurant(dish);
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public Dish getDishById(Long  id) {
        return dishPersistencePort.getDishById(id);
    }

    @Override
    public List<Dish> getAllDishes() {
        return dishPersistencePort.getAllDishes();
    }

    @Override
    public PageModel<Dish> getAllDishesByRestaurantByCategory(Integer page, Integer pageSize, Integer idRestaurant, Integer idCategory) {
        return dishPersistencePort.getAllDishesByRestaurantByCategory(page,pageSize,idRestaurant,idCategory);
    }

    @Override
    public void updateDish(Dish dish) {
        Dish newDish = dishPersistencePort.getDishById(dish.getId());
        if (newDish == null){
            throw new NoDataFoundException();

        }
        validateUserIsOwnerRestaurant(newDish);
       newDish.setPrice(dish.getPrice());
       newDish.setDescription(dish.getDescription());
       dishPersistencePort.updateDish(newDish);
    }

    @Override
    public void changeStateDish(Dish  dish) {
        Dish newdish = dishPersistencePort.getDishById(dish.getId());
        if (newdish == null){
            throw new NoDataFoundException();

        }
        validateUserIsOwnerRestaurant(newdish);
        newdish.setActive(!newdish.getActive());
       dishPersistencePort.changeStateDish(newdish);
    }

    public static void validateDish(Dish dish) {
        List<String> errors = new ArrayList<>();

        String name = dish.getName();
        if (name == null || name.isBlank()) {
            errors.add(ExceptionsConstants.NAME_REQUIRED);
        }


        int price = dish.getPrice();
        if (price < ValuesConstants.MIN_VALUE_FOR_NUMBERS ) {
            errors.add(ExceptionsConstants.PRICE_REQUIRED);
        }

        String description = dish.getDescription();
        if (description == null || description.isBlank()) {
            errors.add(ExceptionsConstants.DESCRIPTION_REQUIRED);
        }
        String urlImage = dish.getUrlImage();
        if (urlImage == null || urlImage.isBlank()) {
            errors.add(ExceptionsConstants.URL_REQUIRED);
        }
        long idCategory = dish.getIdCategory();
        if (idCategory < ValuesConstants.MIN_VALUE_FOR_NUMBERS ) {
            errors.add(ExceptionsConstants.ID_CATEGORY_REQUIRED);
        }

        long idRestaurant = dish.getIdRestaurant();
        if (idRestaurant < ValuesConstants.MIN_VALUE_FOR_NUMBERS ) {
            errors.add(ExceptionsConstants.ID_RESTAURANT_REQUIRED);
        }
        if (!errors.isEmpty()) {
            throw new DishValidationException(errors);
        }
    }
    public void validateUserIsOwnerRestaurant ( Dish dish){

        Restaurant restaurant = restaurantPersistencePort.getRestaurantById(dish.getIdRestaurant());

        long idUser = utilsServicePort.getCurrentUserId();
        if ( idUser != restaurant.getId_owner()){
            throw new UserIsNotOwnerValidationException(ExceptionsConstants.USER_NOT_OWNER_RESTAURANT_EXCEPTION);
        }
    }
}
