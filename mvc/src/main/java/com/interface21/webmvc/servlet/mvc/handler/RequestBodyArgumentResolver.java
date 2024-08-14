package com.interface21.webmvc.servlet.mvc.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interface21.web.bind.annotation.RequestBody;
import com.interface21.webmvc.servlet.mvc.support.HttpRequestBodyParser;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestBodyArgumentResolver implements ArgumentResolver {
    private final ObjectMapper objectMapper;

    public RequestBodyArgumentResolver() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestBody.class);
    }

    @Override
    public Object resolve(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contentType = request.getHeader("Content-Type");
        if (!contentType.contains("application/json")) {
            throw new IllegalArgumentException("Content-Type이 application/json이어야 합니다. contentType=" + contentType);
        }

        String requestBody = HttpRequestBodyParser.parse(request);
        Class<?> type = parameter.getType();

        Object body = objectMapper.readValue(requestBody, type);
        return type.cast(body);
    }
}
