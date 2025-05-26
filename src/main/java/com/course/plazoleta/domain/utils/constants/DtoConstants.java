package com.course.plazoleta.domain.utils.constants;

public class DtoConstants {
    private DtoConstants() {

    }

    // Regular expressions
    public static final String PHONE_REGEX = "^\\+?\\d{1,12}$";
    public static final String ONLY_NUMBERS_REGEX ="^\\d+$";
    public static final String NOT_ONLY_NUMBERS_REGEX =".*\\D.*";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";



    public static final String FIELD_REQUIRED = "This field is required";
    public static final String FIELD_NOT_ONLY_NUMBER_MESSAGE = "The name cannot be composed only of numbers";
    public static final String FIELD_NOT_NEGATIVE_MESSAGE = "The field cannot be negative or 0";
    public static final String FIELD_MUST_HAVE_13_CHARACTERS= "The field must be at most 13 characters long";


    public static final String FIELD_PHONE_FORMAT_MESSAGE = "Invalid phone number format";
    public static final String FIELD_URL_INVALID_FORMAT_MESSAGE="Invalid URL format" ;
    public static final String FIELD_ONLY_NUMBER_REQUIRED = "The field must only contain numbers";

    //NIT
    public static final String NIT_SCHEMA_DESCRIPTION="NIT" ;
    public static final String NIT_SCHEMA_EXAMPLE = "145254";
    //DESCRIPTION
    public static final String DESCRIPTION_SCHEMA_DESCRIPTION="Restaurant name" ;
    public static final String DESCRIPTION_SCHEMA_EXAMPLE="Restaurant" ;

    public static final String MUST_BE_ONE_DISH= "There must be at least one dish on the list";

    public static final String FIELD_PHONE_REQUIRED = "The phone is required";
    public static final String FIELD_MESSAGE_REQUIRED = "The message is required";
}
