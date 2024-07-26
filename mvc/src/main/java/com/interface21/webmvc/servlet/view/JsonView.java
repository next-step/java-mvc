package com.interface21.webmvc.servlet.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.SerializationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.interface21.web.http.MediaType;
import com.interface21.webmvc.servlet.View;

public class JsonView implements View {

    private final ObjectMapper om = new ObjectMapper();

    @Override
    public void render(
            final Map<String, ?> model,
            final HttpServletRequest request,
            HttpServletResponse response) {
        serializedModel(model, response);
    }

    private void serializedModel(Map<String, ?> model, HttpServletResponse response) {

        try (final var writer = response.getWriter()) {
            writeValue(model, response, writer);
        } catch (IOException e) {
            throw new SerializationException("Failed to serialize model", e);
        }
    }

    private void writeValue(Map<String, ?> model, HttpServletResponse response, PrintWriter writer)
            throws IOException {
        if (model.size() == 1) {
            writer.write(model.toString());
            return;
        }
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        om.writeValue(writer, model);
    }
}
