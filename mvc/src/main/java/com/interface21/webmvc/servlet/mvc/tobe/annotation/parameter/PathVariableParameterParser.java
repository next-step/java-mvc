package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Parameter;

import static com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter.SupportParameterType.parse;

public class PathVariableParameterParser implements MethodParameterParser {

    private final Class<?> parameterType;
    private final String parameterName;
    private final PathParameter pathParameter;

    private PathVariableParameterParser(Class<?> parameterType, String parameterName, PathParameter pathParameter) {
        this.parameterType = parameterType;
        this.parameterName = parameterName;
        this.pathParameter = pathParameter;
    }

    public static PathVariableParameterParser of(String urlPattern, Parameter parameter) {
        return new PathVariableParameterParser(
                parameter.getType(),
                parameter.getName(),
                PathParameter.of(urlPattern, parameter)
        );
    }

    @Override
    public boolean accept() {
        return pathParameter.isPathVariable();
    }

    @Override
    public Object parseValue(HttpServletRequest request, HttpServletResponse response) {
        String parseValue = pathParameter.parsePathVariable(request.getRequestURI(), parameterName);
        return parseToType(parseValue);
    }

    private Object parseToType(String parseValue) {
        return parse(parameterType, parseValue);
    }
}
