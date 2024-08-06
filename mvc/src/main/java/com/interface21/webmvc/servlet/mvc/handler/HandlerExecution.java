package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.webmvc.servlet.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Objects;

public class HandlerExecution {

    private final Object declaredObject;
    private final Method method;

    public HandlerExecution(Object declaredObject, Method method) {
        this.declaredObject = declaredObject;
        this.method = method;
    }

    public HandlerExecution(Object handler, String methodName) {
        this(handler, getMethod(handler, methodName));
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(declaredObject, request, response);
    }

    private static Method getMethod(Object handler, String methodName) {
        try {
            return handler.getClass()
                    .getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("요청된 method name에 해당하는 핸들링 메소드가 없습니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HandlerExecution that = (HandlerExecution) o;
        return Objects.equals(declaredObject.getClass(), that.declaredObject.getClass()) && Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(declaredObject, method);
    }
}
