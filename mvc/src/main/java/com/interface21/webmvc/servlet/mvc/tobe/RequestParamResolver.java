package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestParam;
import com.interface21.webmvc.servlet.mvc.tobe.support.TypeCheckUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class RequestParamResolver implements ArgumentResolver {
    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestParam.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Parameter parameter) {
        RequestParam annotation = parameter.getAnnotation(RequestParam.class);
        String paramName = annotation.value().isEmpty() ? parameter.getName() : annotation.value();
        String paramValue = request.getParameter(paramName);

        if (paramValue == null && annotation.required()) {
            throw new IllegalArgumentException("Required parameter '" + paramName + "' is not present");
        }

        return TypeCheckUtil.convertStringToTargetType(paramValue, parameter.getType());
    }
}