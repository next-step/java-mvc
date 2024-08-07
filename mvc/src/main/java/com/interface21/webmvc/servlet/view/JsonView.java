package com.interface21.webmvc.servlet.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interface21.webmvc.servlet.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public class JsonView implements View {


    private static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    private final String contentType;

    public JsonView() {
        this.contentType = APPLICATION_JSON_UTF8;
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, HttpServletResponse response) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(contentType);

        String responseBody = objectMapper.writeValueAsString(model);
        response.setContentLength(responseBody.length());
        response.getWriter().write(responseBody);
    }
}
