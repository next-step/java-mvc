package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServletRequestResponse {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public ServletRequestResponse(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Object getParameter(String parameterName) {
        return request.getParameter(parameterName);
    }

    public static boolean isRequestType(Class<?> cls) {
        return HttpServletRequest.class.isAssignableFrom(cls);
    }

    public static boolean isResponseType(Class<?> cls) {
        return HttpServletResponse.class.isAssignableFrom(cls);
    }
}
