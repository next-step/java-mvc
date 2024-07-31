package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Parameter;

public class IntegerParameterBinder implements ParameterBinder {

    @Override
    public boolean supports(final Parameter parameter) {
        final Class<?> parameterType = parameter.getType();
        return parameterType.equals(int.class) || parameterType.equals(Integer.class);
    }

    @Override
    public Object bind(final Object arg, final Parameter parameter) {
        return Integer.parseInt(arg.toString());
    }
}
