package com.interface21.webmvc.servlet.mvc.tobe.parameterresolver;

import com.interface21.core.util.ConversionUtil;
import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerKey;
import com.interface21.webmvc.servlet.mvc.tobe.ParameterResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;

public class RequestParameterResolver implements ParameterResolver {
    private static final List<Type> SIMPLE_PRIMITIVE_TYPES = List.of(
            int.class,
            long.class,
            double.class,
            byte.class,
            short.class,
            float.class,
            boolean.class,
            char.class,
            char[].class
    );

    private static final List<Type> SIMPLE_TYPES = List.of(
            String.class,
            Long.class,
            Integer.class,
            Long.class,
            Double.class,
            Byte.class,
            Short.class,
            Float.class,
            Boolean.class,
            Character.class
    );

    @Override
    public boolean accept(HttpServletRequest request, Parameter parameter) {
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return false;
        }

        Class<?> parameterType = parameter.getType();

        return SIMPLE_PRIMITIVE_TYPES.contains(parameterType) || SIMPLE_TYPES.contains(parameterType);
    }

    @Override
    public Object resolve(HttpServletRequest request,
                          HttpServletResponse response,
                          Parameter parameter,
                          HandlerKey handlerKey) {
        String rawParameterValue = request.getParameter(parameter.getName());
        Class<?> parameterType = parameter.getType();

        return ConversionUtil.boxPrimitiveValue(rawParameterValue, parameterType);
    }
}
