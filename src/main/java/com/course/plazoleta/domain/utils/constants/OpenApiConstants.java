package com.course.plazoleta.domain.utils.constants;

public class OpenApiConstants {

    private OpenApiConstants() {

    }

    public static final String VALIDATIONS_ERRORS_MESSAGE = "Validation errors";
    public static final String NO_DATA_MESSAGE = "No data found";

    public static final String TITLE_SWAGGER_BEARER_TOKEN ="Beare";

    // RESTAURANT CONSTANTS API
    public static final String TITLE_RESTAURANT_REST = "RESTAURANT";
    public static final String TITLE_DESCRIPTION_RESTAURANT_REST = "Endpoints for restaurants";

    public static final String NEW_RESTAURANT_TITLE = "Add a new restaurant";
    public static final String NEW_RESTAURANT_CREATED_MESSAGE = "Restaurant created";

    public static final String GET_RESTAURANT_TITLE = "Get restaurant by ID";
    public static final String GET_RESTAURANT_MESSAGE = "Restaurant found";

    public static final String GET_ALL_RESTAURANT_TITLE = "Get all restaurants";
    public static final String GET_ALL_RESTAURANT_MESSAGE = "All restaurants returned";

    public static final String DELETE_RESTAURANT_TITLE = "Delete restaurant by ID";
    public static final String DELETE_RESTAURANT_MESSAGE = "Restaurant deleted";


    // DISH CONSTANTS API
    public static final String TITLE_DISH_REST = "Dish";
    public static final String TITLE_DESCRIPTION_DISH_REST = "Endpoints for dishes";

    public static final String NEW_DISH_TITLE = "Add a new dish";
    public static final String NEW_DISH_CREATED_MESSAGE = "Dish created";

    public static final String GET_DISH_TITLE = "Get dish by ID";
    public static final String GET_DISH_MESSAGE = "Dish found";

    public static final String GET_ALL_DISH_TITLE = "Get all dishes";
    public static final String GET_ALL_DISH_MESSAGE = "All dishes returned";

    public static final String GET_ALL_DISH_BY_RESTAURANT_CATEGORY_TITLE = "Get all dishes by restaurant and category";
    public static final String GET_ALL_DISH_BY_RESTAURANT_CATEGORY_MESSAGE = "All dishes filtered returned";

    public static final String UPDATE_DISH_TITLE = "Update dish";
    public static final String UPDATE_DISH_MESSAGE = "Modified dish";
    public static final String UPDATE_NOT_EXIST_DISH_MESSAGE ="The dish does not exist";

    public static final String CHANGE_STATE_DISH_TITLE = "Change State Dish";
    public static final String CHANGE_STATE_DISH_MESSAGE = "Dish state change";

    // CATEGORY CONSTANTS API
    public static final String TITLE_CATEGORY_REST = "Category";
    public static final String TITLE_DESCRIPTION_CATEGORY_REST = "Endpoints for categories";

    public static final String NEW_CATEGORY_TITLE = "Add a new category";
    public static final String NEW_CATEGORY_CREATED_MESSAGE = "Category created";

    public static final String GET_CATEGORY_TITLE = "Get category by ID";
    public static final String GET_CATEGORY_MESSAGE = "Category found";

    public static final String GET_ALL_CATEGORY_TITLE = "Get all categories";
    public static final String GET_ALL_CATEGORY_MESSAGE = "All categories returned";

    // ORDER CONSTANTS API
    public static final String TITLE_ORDER_REST = "ORDER";
    public static final String TITLE_DESCRIPTION_ORDER_REST = "Endpoints for orders";

    public static final String NEW_ORDER_TITLE = "Add a new order";
    public static final String NEW_ORDER_CREATED_MESSAGE = "Order created";

    public static final String GET_FILTER_ORDERS_TITLE = "Get orders";
    public static final String GET_FILTER_ORDERS_MESSAGE = "Order returned";

    public static final String TAKE_ORDER_TITLE = "Take Order";
    public static final String TAKE_ORDER_MESSAGE = "Order taken";

    public static final String COMPLETE_ORDER_TITLE = "Complete Order";
    public static final String COMPLETE_ORDER_MESSAGE = "Order completed";

    public static final String DELIVERED_ORDER_TITLE = "Deliver Order";
    public static final String DELIVERED_ORDER_MESSAGE = "Order delivered";
}
