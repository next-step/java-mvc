package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.webmvc.servlet.mvc.tobe.support.TypeCheckUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class PathVariableResolver implements ArgumentResolver {
    private final String urlPattern;

    public PathVariableResolver(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Parameter parameter) {
        PathVariable annotation = parameter.getAnnotation(PathVariable.class);
        String variableName = annotation.value().isEmpty() ? parameter.getName() : annotation.value();
        String value = extractPathVariable(request.getRequestURI(), urlPattern, variableName);
        return TypeCheckUtil.convertStringToTargetType(value, parameter.getType());
    }

    private String extractPathVariable(String requestUri, String urlPattern, String variableName) {
        String[] uriParts = requestUri.split("/");
        String[] patternParts = urlPattern.split("/");
        for (int i = 0; i < patternParts.length; i++) {
            if (patternParts[i].equals("{" + variableName + "}")) {
                return uriParts[i];
            }
        }
        throw new IllegalArgumentException("Path variable not found: " + variableName);
    }
}