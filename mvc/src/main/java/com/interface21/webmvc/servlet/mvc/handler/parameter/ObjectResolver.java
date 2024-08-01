package com.interface21.webmvc.servlet.mvc.handler.parameter;

import com.interface21.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public class ObjectResolver implements ArgumentResolver {

    @Override
    public boolean supportsParameter(Parameter parameter) {
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return false;
        }
        return !BasicArgumentType.isBasicType(parameter.getType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Parameter parameter = methodParameter.getParameter();
        Class<?> parameterType = parameter.getType();
        Object parameterInstance = parameterType.getConstructor().newInstance();

        for (Field field : parameterType.getDeclaredFields()) {
            String fieldName = field.getName();
            String parameterValue = request.getParameter(fieldName);
            field.setAccessible(true);
            field.set(parameterInstance, BasicArgumentType.convert(parameterValue, field.getType()));
            field.setAccessible(false);
        }

        return parameterInstance;
    }
}
