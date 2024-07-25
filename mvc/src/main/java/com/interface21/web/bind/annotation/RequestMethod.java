package com.interface21.web.bind.annotation;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum RequestMethod {
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    private static final Map<String, RequestMethod> MAPPING;

    static {
        MAPPING =
                Arrays.stream(RequestMethod.values())
                        .map(it -> Map.entry(it.name(), it))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static RequestMethod from(String method) {
        return Optional.ofNullable(MAPPING.get(method.toUpperCase(Locale.ROOT)))
                .orElseThrow(
                        () -> new IllegalArgumentException("No such RequestMethod: " + method));
    }
}
