package com.interface21.webmvc.servlet.mvc.tobe;

public class DefaultMethodArgumentResolver implements MethodArgumentResolver {

    @Override
    public boolean supports(MethodParameter methodParameter) {
        return !methodParameter.hasAnnotation() && methodParameter.isNativeType();
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ServletRequestResponse request) {
        String parameterName = methodParameter.getParameterName();
        return request.getParameter(parameterName);
    }
}
