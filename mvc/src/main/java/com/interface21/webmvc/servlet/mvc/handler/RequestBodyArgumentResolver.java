package com.interface21.webmvc.servlet.mvc.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interface21.web.bind.annotation.RequestBody;
import com.interface21.web.http.MediaType;
import com.interface21.webmvc.servlet.mvc.support.HttpRequestBodyParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestBodyArgumentResolver implements ArgumentResolver {
    private final HttpRequestBodyParser httpRequestBodyParser;

    public RequestBodyArgumentResolver() {
        this.httpRequestBodyParser = new HttpRequestBodyParser();
    }

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestBody.class);
    }

    @Override
    public Object resolve(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contentType = request.getHeader("Content-Type");
        if (!contentType.equals(MediaType.APPLICATION_JSON_VALUE)) {
            throw new IllegalArgumentException("Content-Type 헤더가 JSON이 아님");
        }

        String requestBody = httpRequestBodyParser.parse(request);
        ObjectMapper objectMapper = new ObjectMapper();
        Class<?> type = parameter.getType();
        Object body = objectMapper.readValue(requestBody, type);
        return type.cast(body);
    }
}
