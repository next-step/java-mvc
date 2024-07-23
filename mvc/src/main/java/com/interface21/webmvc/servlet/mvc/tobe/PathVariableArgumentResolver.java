package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PathVariableArgumentResolver implements HandlerMethodArgumentResolver {

    private final String urlPattern;

    public PathVariableArgumentResolver(final String urlPattern) {
        this.urlPattern = urlPattern;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.isPathVariable();
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final HttpServletRequest request, final HttpServletResponse response) {
        final String variableName = getVariableName(parameter);
        final String requestUri = request.getRequestURI();
        final String variableValue = PathPatternUtil.getUriValue(urlPattern, requestUri, variableName);
        return BasicType.convert(parameter.getParameterType(), variableValue);
    }

    private String getVariableName(final MethodParameter parameter) {
        final String variableName = parameter.getPathVariableValue();
        if (variableName != null && !variableName.isEmpty()) {
            return variableName;
        }
        return parameter.getParameterName();
    }

}
