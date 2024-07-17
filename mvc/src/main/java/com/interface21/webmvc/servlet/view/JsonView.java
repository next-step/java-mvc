package com.interface21.webmvc.servlet.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interface21.web.http.MediaType;
import com.interface21.webmvc.servlet.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final PrintWriter out = response.getWriter();

        if (model.size() == 1) {
            final Object singleValue = model.values().iterator().next();
            if (isPrimitiveOrString(singleValue)) {
                response.setContentType(MediaType.TEXT_PLAIN);
                out.print(singleValue);
            } else {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                out.print(mapper.writeValueAsString(singleValue));
            }
        } else {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            out.print(mapper.writeValueAsString(model));
        }
        out.flush();
    }

    private boolean isPrimitiveOrString(final Object value) {
        return value instanceof String ||
                value instanceof Boolean ||
                value instanceof Character ||
                value instanceof Byte ||
                value instanceof Short ||
                value instanceof Integer ||
                value instanceof Long ||
                value instanceof Float ||
                value instanceof Double ||
                value.getClass().isPrimitive();
    }
}
