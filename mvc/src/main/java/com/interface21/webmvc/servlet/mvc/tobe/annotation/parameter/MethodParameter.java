package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Parameter;
import java.util.List;

public class MethodParameter {

    private final List<MethodParameterParser> methodParameterParsers;
    private final MethodParameterParser defaultParameterParser;

    private MethodParameter(List<MethodParameterParser> methodParameterParsers, MethodParameterParser defaultParameterParser) {
        this.methodParameterParsers = methodParameterParsers;
        this.defaultParameterParser = defaultParameterParser;
    }

    public static MethodParameter of(String urlPattern, Parameter parameter) {
        return new MethodParameter(
                List.of(
                        new HttpServletRequestParameterParser(parameter.getType()),
                        new HttpServletResponseParameterParser(parameter.getType()),
                        PathVariableParameterParser.of(urlPattern, parameter),
                        SupportTypeParameterParser.from(parameter)
                ),
                new ObjectParameterParser(parameter.getType())
        );
    }

    public Object parseValue(HttpServletRequest request, HttpServletResponse response) {
        MethodParameterParser methodParameterParser = methodParameterParsers.stream()
                .filter(MethodParameterParser::accept)
                .findAny()
                .orElse(defaultParameterParser);
        return methodParameterParser.parseValue(request, response);
    }

    public List<MethodParameterParser> getMethodParameterParsers() {
        return methodParameterParsers;
    }

    public MethodParameterParser getDefaultParameterParser() {
        return defaultParameterParser;
    }
}
