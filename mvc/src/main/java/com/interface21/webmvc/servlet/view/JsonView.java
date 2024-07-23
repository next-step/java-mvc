package com.interface21.webmvc.servlet.view;

import com.interface21.core.util.JsonUtils;
import static com.interface21.web.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import com.interface21.webmvc.servlet.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public class JsonView implements View {

    private final String contentType;

    public JsonView() {
        this.contentType = APPLICATION_JSON_UTF8_VALUE;
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = JsonUtils.toJson(model);
        response.setContentType(contentType);
        response.setContentLength(body.length());
        response.getWriter().write(body);
    }
}
