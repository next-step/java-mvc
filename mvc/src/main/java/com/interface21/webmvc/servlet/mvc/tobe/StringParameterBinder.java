package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Parameter;

public class StringParameterBinder implements ParameterBinder{

    public static final String DEFAULT_STRING = "";

    @Override
    public boolean supports(final Parameter parameter) {
        return parameter.getType().equals(String.class);
    }

    @Override
    public Object bind(final Object arg, final Parameter parameter) {
        if (arg == null) {
            return DEFAULT_STRING;
        }

        return arg.toString();
    }
}
