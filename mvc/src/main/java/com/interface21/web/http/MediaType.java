package com.interface21.web.http;

public enum MediaType {
    APPLICATION_JSON_UTF8_VALUE("application/json;charset=UTF-8"),
    FORM_URL_ENCODED("application/x-www-form-urlencoded");

    private final String value;

    MediaType(String value) {
        this.value = value;
    }

    public static MediaType from(String value) {
        return MediaType.valueOf(value);
    }

    public String getValue() {
        return value;
    }
}
