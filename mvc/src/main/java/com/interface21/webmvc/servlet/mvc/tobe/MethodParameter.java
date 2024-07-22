package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.PathVariable;

import java.lang.reflect.Parameter;

public class MethodParameter {

    private final Parameter parameter;

    public MethodParameter(final Parameter parameter) {
        this.parameter = parameter;
    }

    public String getParameterName() {
        return parameter.getName();
    }

    public Class<?> getParameterType() {
        return parameter.getType();
    }

    public boolean isPathVariable() {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    public String getPathVariableValue() {
        final PathVariable annotation = parameter.getAnnotation(PathVariable.class);
        if (annotation == null) {
            return "";
        }
        return annotation.value();
    }
}
