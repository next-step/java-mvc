package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Parameter;

public class LongParameterBinder implements ParameterBinder {

    @Override
    public boolean supports(final Parameter parameter) {
        final Class<?> parameterType = parameter.getType();
        return parameterType.equals(long.class) || parameterType.equals(Long.class);
    }

    @Override
    public Object bind(final Object arg, final Parameter parameter) {
        return Long.parseLong(arg.toString());
    }
}
