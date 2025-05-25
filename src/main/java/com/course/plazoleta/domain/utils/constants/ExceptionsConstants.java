package com.course.plazoleta.domain.utils.constants;

public class ExceptionsConstants {

    public static final String NAME_REQUIRED = "The name  is required";
    public static final String ADDRESS_REQUIRED = "The address  is required";
    public static final String ID_OWNER_REQUIRED = "The owner  is required";
    public static final String URL_REQUIRED = "The url  is required";
    public static final String NIT_REQUIRED = "The nit  is required";
    public static final String PRICE_REQUIRED = "The price must be positive and more than 1 ";
    public static final String DESCRIPTION_REQUIRED = "The description is required";

    public static final String ID_CATEGORY_REQUIRED = "The category  is required";
    public static final String ID_RESTAURANT_REQUIRED = "The restaurant  is required";

    public static final String NIT_FIELD_ONLY_NUMBER_REQUIRED ="The nit is only numbers";

    public static final String PHONE_FORMAT_ERROR = "The format of phone  is incorrect";
    public static final String PHONE_MUST_HAVE_LESS_THAN_13_CHARACTERS = "the phone must be less than 13 characters";

    public static final String USER_NOT_FOUND_EXCEPTION = "User not found";
    public static final String USER_NOT_OWNER_EXCEPTION = "The user must be a owner";

    public static final String USER_NOT_OWNER_RESTAURANT_EXCEPTION = "User is not owner of this restaurant";
    public static final String CLIENT_HAVE_ORDER_ACTIVE_EXCEPTION = "The client already has an active order";
    public static final String DISH_NOT_FOUND_EXCEPTION = "The dish not exits";
}
