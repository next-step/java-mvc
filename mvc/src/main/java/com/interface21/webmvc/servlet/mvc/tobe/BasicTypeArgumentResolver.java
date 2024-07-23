package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BasicTypeArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return BasicType.isSupportedType(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final HttpServletRequest request, final HttpServletResponse response) {
        final String parameterName = parameter.getParameterName();
        final String parameterValue = request.getParameter(parameterName);
        if (parameterValue == null) {
            return null;
        }

        return BasicType.convert(parameter.getParameterType(), parameterValue);
    }
}
