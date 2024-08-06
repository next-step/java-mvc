package com.interface21.webmvc.servlet.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interface21.web.http.MediaType;
import com.interface21.webmvc.servlet.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public class JsonView implements View {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int SINGLE_MODEL_SIZE = 1;

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String body = body(model);
        setResponseHeaders(response, body.length(), isPlainText(model));
        response.getWriter().write(body);
    }

    private String body(final Map<String, ?> model) throws Exception {
        if (isPlainText(model)) {
            return (String) model.values().iterator().next();
        }

        return OBJECT_MAPPER.writeValueAsString(model);
    }

    private boolean isPlainText(final Map<String, ?> model) {
        return SINGLE_MODEL_SIZE == model.size();
    }

    private void setResponseHeaders(final HttpServletResponse response, final int contentLength, final boolean isPlainText) {
        response.setContentLength(contentLength);

        if (isPlainText) {
            response.setContentType(MediaType.TEXT_PLAIN);
            return;
        }
        
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
}
