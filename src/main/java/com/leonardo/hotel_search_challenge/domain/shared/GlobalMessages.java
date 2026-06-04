package com.leonardo.hotel_search_challenge.domain.shared;

public class GlobalMessages {

    private GlobalMessages(){}

    public static final String NULL_FIELD_MESSAGE_ERROR = "El campo %s no puede ser nulo";
    public static final String BLANK_OR_EMPTY_FIELD_MESSAGE_ERROR = "El campo %s no puede estar vacío";
    public static final String NEGATIVE_FIELD_MESSAGE_ERROR = "El campo %s no puede ser negativo";
    public static final String CHECK_IN_BEFORE_CHECK_OUT_MESSAGE_ERROR = "El checkIn debe ser anterior al checkOut";
    public static final String CHECK_IN_BEFORE_TODAY_ERROR = "El checkIn no puede ser anterior al día de hoy";

}
