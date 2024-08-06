package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServletRequestMethodArgumentResolver implements MethodArgumentResolver {

    @Override
    public boolean supports(MethodParameter methodParameter) {
        return isRequestType(methodParameter.getDeclaredType())
                || isResponseType(methodParameter.getDeclaredType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ServletRequestResponse request) {

        if (methodParameter.isType(HttpServletRequest.class)) {
            return request.getRequest();
        }
        return request.getResponse();
    }

    private static boolean isRequestType(Class<?> cls) {
        return HttpServletRequest.class.isAssignableFrom(cls);
    }

    private static boolean isResponseType(Class<?> cls) {
        return HttpServletResponse.class.isAssignableFrom(cls);
    }
}
