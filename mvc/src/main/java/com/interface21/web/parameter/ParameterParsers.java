package com.interface21.web.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ParameterParsers {

    private final List<ParameterParser> parsers = new ArrayList<>(Arrays.asList(new PathVariableParser(), new QueryParamParser()));

    public ParameterParsers() {

    }

    public ParameterParsers(ParameterParser... parsers) {
        this.parsers.addAll(Arrays.asList(parsers));
    }

    public Object[] parse(final Method method, final HttpServletRequest request, final HttpServletResponse response) {
        var parameters = method.getParameters();
        var params = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            var parsedParam = parse(method, parameters[i], request, response);
            params[i] = parsedParam;
        }
        return params;
    }

    private Object parse(final Method method, final Parameter parameter, final HttpServletRequest request, final HttpServletResponse response) {
        if (parameter.getType().equals(HttpServletRequest.class)) {
            return request;
        }
        if (parameter.getType().equals(HttpServletResponse.class)) {
            return response;
        }

        return parsers.stream().map(parser -> parser.parse(method, parameter, request)).filter(Objects::nonNull).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
