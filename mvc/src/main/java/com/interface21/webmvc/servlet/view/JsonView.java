package com.interface21.webmvc.servlet.view;

import com.interface21.webmvc.servlet.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public class JsonView implements View {
    private final StringModelWriter stringModelWriter = new StringModelWriter();
    private final ObjectModelWriter objectModelWriter = new ObjectModelWriter();

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        if (stringModelWriter.support(model)) {
            stringModelWriter.write(model, response);
            return;
        }

        objectModelWriter.write(model, response);
    }
}
