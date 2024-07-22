package com.interface21.webmvc.servlet.mvc.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonConverter {

    private static final Logger log = LoggerFactory.getLogger(JsonConverter.class);

    private final ObjectMapper objectMapper;

    public JsonConverter() {
        this.objectMapper = new ObjectMapper();
    }

    public String convertModelToJson(Map<String, ?> model) {
        try {
            if (model.size() == 1) {
                Object value = model.values().iterator().next();
                return objectMapper.writeValueAsString(value);
            }

            return objectMapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 실패", e);
            throw new RuntimeException(e);
        }
    }
}
