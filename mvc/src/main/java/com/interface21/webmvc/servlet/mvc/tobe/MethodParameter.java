package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Parameter;

public class MethodParameter {
    private final Parameter parameter;

    public MethodParameter(final Parameter parameter) {
        this.parameter = parameter;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public String getParameterName() {
        return parameter.getName();
    }

    public Class<?> getParameterType() {
        return parameter.getType();
    }

}
