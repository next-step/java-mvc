package com.interface21.webmvc.servlet.view;

import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.exception.MessageConverterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public class JsonView implements View {
    private final List<ModelWriter> modelWriters;

    public JsonView() {
        modelWriters = List.of(new StringModelWriter(), new ObjectModelWriter());
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ModelWriter converter = modelWriters.stream()
                .filter(modelWriter -> modelWriter.support(model))
                .findFirst()
                .orElseThrow(() -> new MessageConverterException("Proper MessageConverter is not registered"));

        converter.write(model, response);
    }
}
