package com.interface21.webmvc.servlet.mvc.support;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryStringParser {

    private static final String QUERY_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    public Map<String, String> parse(String queryString) {
        return Arrays.stream(queryString.split(QUERY_SEPARATOR))
                .map(query -> query.split(KEY_VALUE_SEPARATOR))
                .collect(Collectors.toUnmodifiableMap(
                        keyValue -> keyValue[0],
                        keyValue -> {
                            if (keyValue.length == 1) {
                                return "";
                            }
                            return keyValue[1];
                        }
                ));
    }
}
