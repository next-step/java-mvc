package com.interface21.webmvc.servlet.view;

import com.interface21.web.http.MediaType;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class StringModelWriter implements ModelWriter {

    public static final int MIN_MODEL_SIZE = 1;

    @Override
    public boolean support(final Map<String, ?> model) {
        return model.size() == MIN_MODEL_SIZE && isPrimitiveOrString(getFirstValue(model));
    }

    private boolean isPrimitiveOrString(final Object value) {
        return value instanceof String ||
                value instanceof Boolean ||
                value instanceof Character ||
                value instanceof Number;
    }

    @Override
    public void write(final Map<String, ?> model, final HttpServletResponse response) throws IOException {
        final PrintWriter out = response.getWriter();
        response.setContentType(MediaType.TEXT_PLAIN);
        out.print(getFirstValue(model));
    }

    private Object getFirstValue(final Map<String, ?> model) {
        return model.values().iterator().next();
    }

}
