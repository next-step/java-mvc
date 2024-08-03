package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Method;

public final class MethodParameterProvider {

    private MethodParameterProvider() {}

    public static MethodParameter[] create(final Method method) {
        var parameters = method.getParameters();
        var methodParameters = new MethodParameter[parameters.length];

        if (parameters.length == 0) {
            return new MethodParameter[] {new MethodParameter(-1, method)};
        }

        for (var i = 0; i < parameters.length; i++) {
            methodParameters[i] = new MethodParameter(i, parameters[i], method);
        }
        return methodParameters;
    }
}
