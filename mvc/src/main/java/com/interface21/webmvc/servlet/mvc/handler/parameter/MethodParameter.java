package com.interface21.webmvc.servlet.mvc.handler.parameter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodParameter {

    private final Method method;
    private final Parameter parameter;

    public MethodParameter(Method method, Parameter parameter) {
        this.method = method;
        this.parameter = parameter;
    }

    public Method getMethod() {
        return method;
    }

    public Parameter getParameter() {
        return parameter;
    }
}
