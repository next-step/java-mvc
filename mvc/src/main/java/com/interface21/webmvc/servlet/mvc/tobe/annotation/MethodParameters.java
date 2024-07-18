package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MethodParameters {

    private final List<MethodParameter> values;

    public MethodParameters(Method method) {
        this.values = parseMethodParameters(method);
    }

    private List<MethodParameter> parseMethodParameters(Method method) {
        return Arrays.stream(method.getParameters())
                .map(MethodParameter::new)
                .toList();
    }

    public Object[] parseValues(HttpServletRequest request, HttpServletResponse response) {
        return values.stream()
                .map(parameter -> parameter.parseValue(request, response))
                .toArray();
    }

    public List<MethodParameter> getValues() {
        return values;
    }
}
