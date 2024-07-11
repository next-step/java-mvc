package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public class HandlerExecution {

    private final Object handler;
    private final Method method;

    public HandlerExecution(Object handler, String methodName) {
        this.handler = handler;
        this.method = getMethod(handler, methodName);
    }

    private static Method getMethod(Object handler, String methodName) {
        try {
            return handler.getClass()
                    .getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("요청된 method name에 해당하는 핸들링 메소드가 없습니다.");
        }
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(handler, request, response);
    }
}
