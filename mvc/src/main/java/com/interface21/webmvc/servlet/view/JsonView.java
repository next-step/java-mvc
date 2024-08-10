package com.interface21.webmvc.servlet.view;

import static com.interface21.web.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interface21.webmvc.servlet.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public class JsonView implements View {

    private final ObjectMapper mapper;

    public JsonView(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_JSON_UTF8_VALUE);
        String value = this.mapper.writeValueAsString(model);

        response.setContentLength(value.getBytes().length);
        response
            .getWriter()
            .write(value);
    }
}
