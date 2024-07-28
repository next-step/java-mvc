package com.interface21.webmvc.servlet.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interface21.web.http.MediaType;
import com.interface21.webmvc.servlet.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JsonView implements View {

    public static final String EMPTY_JSON_RESPONSE = "{}";
    private final ObjectMapper objectMapper;

    public JsonView() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, HttpServletResponse response) throws Exception {
        String jsonResponse = createJsonResponse(model);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(jsonResponse);
    }

    private String createJsonResponse(Map<String, ?> model) throws IOException {
        if (model.isEmpty()) {
            return EMPTY_JSON_RESPONSE;
        }
        if (model.size() == 1) {
            return objectMapper.writeValueAsString(model.values().toArray()[0]);
        }
        return objectMapper.writeValueAsString(model);
    }
}
