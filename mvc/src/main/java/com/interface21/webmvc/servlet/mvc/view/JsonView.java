package com.interface21.webmvc.servlet.mvc.view;

import com.interface21.web.http.MediaType;
import com.interface21.webmvc.servlet.mvc.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

public class JsonView implements View {

    private final JsonConverter jsonConverter;

    public JsonView() {
        this.jsonConverter = new JsonConverter();
    }


    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, HttpServletResponse response) throws Exception {
        String jsonBody = jsonConverter.convertModelToJson(model);

        response.getWriter().write(jsonBody);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
}
