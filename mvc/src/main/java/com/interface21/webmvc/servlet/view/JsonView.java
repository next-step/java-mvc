package com.interface21.webmvc.servlet.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interface21.web.http.MediaType;
import com.interface21.webmvc.servlet.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class JsonView implements View {
    private static final Logger log = LoggerFactory.getLogger(JspView.class);

    private final ObjectMapper objectMapper;

    public JsonView() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.addHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        if (model.keySet().size() > 1) {
            try {
                response.getWriter().write(
                        objectMapper.writeValueAsString(model)
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            model.keySet().forEach(key -> {
                log.debug("attribute name : {}, value : {}", key, model.get(key));

                try {
                    response.getWriter().write(
                            objectMapper.writeValueAsString(model.get(key))
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
