package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestParam;
import com.interface21.webmvc.servlet.mvc.tobe.support.TypeCheckUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestParamResolver implements ArgumentResolver {
    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestParam.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Parameter parameter, Method method) {
        RequestParam annotation = parameter.getAnnotation(RequestParam.class);
        String paramName = determineParamName(parameter, annotation);
        String paramValue = extractParamValue(request, paramName);
        validateRequiredParam(paramName, paramValue, annotation);
        return convertParamValue(paramValue, parameter.getType());
    }

    private String determineParamName(Parameter parameter, RequestParam annotation) {
        return annotation.value().isEmpty() ? parameter.getName() : annotation.value();
    }

    private String extractParamValue(HttpServletRequest request, String paramName) {
        return request.getParameter(paramName);
    }

    private void validateRequiredParam(String paramName, String paramValue, RequestParam annotation) {
        if (paramValue == null && annotation.required()) {
            throw new IllegalArgumentException("필수 파라미터가 존재하지 않습니다  : " + paramName);
        }
    }

    private Object convertParamValue(String paramValue, Class<?> targetType) {
        return TypeCheckUtil.convertStringToTargetType(paramValue, targetType);
    }
}
