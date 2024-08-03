package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;

public class ServletRequestMethodArgumentResolver implements MethodArgumentResolver {

    @Override
    public boolean supports(MethodParameter methodParameter) {
        return ServletRequestResponse.isRequestType(methodParameter.getDeclaredType())
                || ServletRequestResponse.isResponseType(methodParameter.getDeclaredType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ServletRequestResponse request) {

        if (methodParameter.isType(HttpServletRequest.class)) {
            return request.getRequest();
        }
        return request.getResponse();
    }
}
