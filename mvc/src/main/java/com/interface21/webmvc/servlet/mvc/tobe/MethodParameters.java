package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.IntStream;

public class MethodParameters {

    private final List<MethodParameter> methodParameters;

    public MethodParameters(List<MethodParameter> methodParameters) {
        this.methodParameters = methodParameters;
    }

    public static MethodParameters emptyParameters(Method method) {
        return new MethodParameters(List.of(new MethodParameter(-1, method)));
    }

    public static MethodParameters from(Method method) {
        var parameters = method.getParameters();
        if (parameters.length == 0) {
            return emptyParameters(method);
        }

        return new MethodParameters(
                IntStream.range(0, parameters.length)
                        .mapToObj(idx -> new MethodParameter(idx, parameters[idx], method))
                        .toList());
    }

    public boolean hasParameters() {
        return !(size() == 1 && indexOf(0).getIndex() == -1);
    }

    public int size() {
        return methodParameters.size();
    }

    public MethodParameter indexOf(int i) {
        return methodParameters.get(i);
    }
}
