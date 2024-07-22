package com.interface21.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
