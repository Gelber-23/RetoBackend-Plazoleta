package com.course.plazoleta.domain.utils.constants;

public class ValuesConstants {

    public static final int MAX_LENGTH_PHONE = 13;
    public static final int MIN_VALUE_FOR_NUMBERS = 1;
    public static final int ID_ROLE_OWNER = 2;
    public static final int ID_ROLE_EMPLOYEE = 3;

    public static final String MIN_VALUE_PAGE_PAGINATION = "0";
    public static final String MIN_VALUE_PAGE_SIZE_PAGINATION = "10";
    public static final String DEFAULT_FIELD_ORDER_RESTAURANT_PAGINATION = "name";

    public static final String DEFAULT_FIELD_ID_RESTAURANT_PAGINATION = "1";
    public static final String DEFAULT_FIELD_ID_CATEGORY_PAGINATION = "1";


    public static final String ROLE_STRING_VALUE_ADMIN = "ROLE_1";
    public static final String ROLE_STRING_VALUE_OWNER= "ROLE_2";
    public static final String ROLE_STRING_VALUE_EMPLOYEE= "ROLE_3";
    public static final String ROLE_STRING_VALUE_CLIENT = "ROLE_4";

    public static final String STATUS_PENDING_ORDER = "PENDING";
    public static final String STATUS_PREPARATION_ORDER = "IN_PREPARATION";
    public static final String STATUS_CANCELED_ORDER = "CANCELED";
    public static final String STATUS_READY_ORDER = "READY";
    public static final String STATUS_DELIVERED_ORDER = "DELIVERED";
    public static final String DEFAULT_FIELD_ORDER_STATE_PAGINATION = "PENDING";

    public static final String DEFAULT_ID_ORDER_TAKE = "0";
    public static final String ALPHABET_CHARACTERS_PIN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int PIN_LENGTH = 5;

    public static final String MESSAGE_ORDER_READY=
            "Good day, sir/madam %s, your order is now ready for pickup.\n" +
                    "You need to show the following pin: %s to be able to pick up your order.";

    public static final String DEFAULT_PIN_ORDER = "A1";
}
