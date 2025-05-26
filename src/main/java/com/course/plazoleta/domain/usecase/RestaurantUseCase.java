package com.course.plazoleta.domain.usecase;

import com.course.plazoleta.domain.api.IRestaurantServicePort;
import com.course.plazoleta.domain.api.IUserServicePort;
import com.course.plazoleta.domain.exception.validation.RestaurantValidationException;
import com.course.plazoleta.domain.exception.usersexception.UserNotOwnerException;
import com.course.plazoleta.domain.model.PageModel;
import com.course.plazoleta.domain.model.Restaurant;
import com.course.plazoleta.domain.model.feign.User;
import com.course.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.course.plazoleta.domain.utils.constants.DtoConstants;
import com.course.plazoleta.domain.utils.constants.ExceptionsConstants;
import com.course.plazoleta.domain.utils.constants.ValuesConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserServicePort userServicePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserServicePort userServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userServicePort = userServicePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        validateRestaurant(restaurant);
        User user = userServicePort.getUserById(restaurant.getId_owner());
        if(user.getRol().getId() == ValuesConstants.ID_ROLE_OWNER)
            restaurantPersistencePort.saveRestaurant(restaurant);
        else
            throw new UserNotOwnerException();
    }

    @Override
    public Restaurant getRestaurantById(long id) {
        return restaurantPersistencePort.getRestaurantById(id);
    }

    @Override
    public PageModel<Restaurant> getAllRestaurants(Integer page , Integer pageSize, String fieldToSort) {
        return restaurantPersistencePort.getAllRestaurants(page,pageSize,fieldToSort);
    }

    @Override
    public void deleteRestaurantById(long id) {
        restaurantPersistencePort.deleteRestaurantById(id);
    }

    public static void validateRestaurant(Restaurant restaurant) {
        List<String> errors = new ArrayList<>();

        String name = restaurant.getName();
        if (name == null || name.isBlank()|| Pattern.matches(DtoConstants.ONLY_NUMBERS_REGEX, name)) {
            errors.add(DtoConstants.FIELD_NOT_ONLY_NUMBER_MESSAGE);
        }


        String address = restaurant.getAddress();
        if (address == null || address.isBlank()) {
            errors.add(ExceptionsConstants.ADDRESS_REQUIRED);
        }

        Long ownerId = restaurant.getId_owner();
        if (ownerId == null) {
            errors.add(ExceptionsConstants.ID_OWNER_REQUIRED);
        } else if (ownerId < ValuesConstants.MIN_VALUE_FOR_NUMBERS) {
            errors.add(DtoConstants.FIELD_NOT_NEGATIVE_MESSAGE);
        }

        String phone = restaurant.getPhone();
        if (phone == null || phone.isBlank() || !Pattern.matches(DtoConstants.PHONE_REGEX, phone)) {
            errors.add(ExceptionsConstants.PHONE_FORMAT_ERROR);
        } else if (phone.length() > ValuesConstants.MAX_LENGTH_PHONE) {
            errors.add(ExceptionsConstants.PHONE_MUST_HAVE_LESS_THAN_13_CHARACTERS);
        }

        String urlLogo = restaurant.getUrlLogo();
        if (urlLogo == null || urlLogo.isBlank()) {
            errors.add(ExceptionsConstants.URL_REQUIRED);
        }

        String nit = restaurant.getNit();
        if (nit == null || nit.isBlank()) {
            errors.add(ExceptionsConstants.NIT_REQUIRED);
        } else if (!Pattern.matches(DtoConstants.ONLY_NUMBERS_REGEX, nit)) {
            errors.add(ExceptionsConstants.NIT_FIELD_ONLY_NUMBER_REQUIRED);
        }

        if (!errors.isEmpty()) {
            throw new RestaurantValidationException(errors);
        }
    }
}
