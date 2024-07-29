package com.interface21.webmvc.servlet.mvc.support;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryStringParser {

    private static final String QUERY_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    public Map<String, String> parse(String queryString) {
        return Arrays.stream(splitQueryString(queryString))
                .map(this::splitKeyAndValue)
                .collect(Collectors.toUnmodifiableMap(this::getKey, this::getValue));
    }

    private String[] splitQueryString(String queryString) {
        return queryString.split(QUERY_SEPARATOR);
    }

    private String[] splitKeyAndValue(String query) {
        return query.split(KEY_VALUE_SEPARATOR);
    }

    private String getKey(String[] keyValue) {
        return keyValue[0];
    }

    private String getValue(String[] keyValue) {
        if (keyValue.length == 1) {
            return "";
        }
        return keyValue[1];
    }
}
