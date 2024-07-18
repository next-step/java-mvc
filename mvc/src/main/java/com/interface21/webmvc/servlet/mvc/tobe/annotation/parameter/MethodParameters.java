package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MethodParameters {

    private final List<MethodParameter> values;

    public MethodParameters(List<MethodParameter> values) {
        this.values = values;
    }

    public static MethodParameters of(String urlPattern, Method method) {
        return new MethodParameters(parseMethodParameters(urlPattern, method));
    }

    private static List<MethodParameter> parseMethodParameters(String urlPattern, Method method) {
        return Arrays.stream(method.getParameters())
                .map(parameter -> MethodParameter.of(urlPattern, parameter))
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
