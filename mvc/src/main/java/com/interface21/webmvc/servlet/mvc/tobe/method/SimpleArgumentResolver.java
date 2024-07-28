package com.interface21.webmvc.servlet.mvc.tobe.method;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SimpleArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return SimpleArgument.isSimpleParameter(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String variableValue =  httpServletRequest.getParameter(methodParameter.getParameterName());
        return SimpleArgument.convert(methodParameter.getParameterType(), variableValue);
    }
}
