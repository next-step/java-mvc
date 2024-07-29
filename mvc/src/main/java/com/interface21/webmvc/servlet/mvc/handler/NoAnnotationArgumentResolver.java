package com.interface21.webmvc.servlet.mvc.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class NoAnnotationArgumentResolver implements ArgumentResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getAnnotations().length == 0;
    }

    @Override
    public Object resolve(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Class<?> type = parameter.getType();
        if (type == HttpServletRequest.class) {
            return request;
        }

        if (type == HttpServletResponse.class) {
            return response;
        }

        throw new IllegalArgumentException("HttpServletRequest, HttpServletResponse 를 제외한 파라미터는 애노테이션으로 역할을 명시해야 합니다.");
    }
}
