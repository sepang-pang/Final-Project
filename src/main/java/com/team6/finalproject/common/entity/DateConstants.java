package com.team6.finalproject.common.entity;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateConstants {
    public static final DateTimeFormatter REQUEST_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final DateTimeFormatter RESPONSE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd (E)", Locale.KOREAN);
    public static final DateTimeFormatter RESPONSE_FORMATTER_DETAIL = DateTimeFormatter.ofPattern("MM/dd (E) a h:mm", Locale.KOREAN);

}