package com.interface21.webmvc.servlet.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interface21.web.http.MediaType;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ObjectModelWriter implements ModelWriter {
    private final ObjectMapper mapper;

    public ObjectModelWriter() {
        mapper = new ObjectMapper();
    }

    @Override
    public boolean support(final Map<String, ?> model) {
        return !model.isEmpty();
    }

    @Override
    public void write(final Map<String, ?> model, final HttpServletResponse response) throws IOException {
        final PrintWriter out = response.getWriter();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        out.print(mapper.writeValueAsString(model));
    }

}
