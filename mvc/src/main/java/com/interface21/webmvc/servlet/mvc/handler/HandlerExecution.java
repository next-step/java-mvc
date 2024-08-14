package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;

public class HandlerExecution {

    private final Object declaredObject;
    private final Method method;
    private final ArgumentResolvers argumentResolvers;

    public HandlerExecution(Object declaredObject, Method method, ArgumentResolvers argumentResolvers) {
        this.declaredObject = declaredObject;
        this.method = method;
        this.argumentResolvers = argumentResolvers;
    }

    public HandlerExecution(Object handler, String methodName) {
        this(handler, getMethod(handler, methodName), new ArgumentResolvers());
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] arguments = new Object[parameters.length];
        for(int i = 0; i < parameters.length; i++) {
            ArgumentResolver argumentResolver = argumentResolvers.findOneSupports(parameters[i]);
            arguments[i] = argumentResolver.resolve(parameters[i], method, request, response);
        }
        return (ModelAndView) method.invoke(declaredObject, arguments);
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HandlerExecution that = (HandlerExecution) o;
        return Objects.equals(declaredObject.getClass(), that.declaredObject.getClass()) && Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(declaredObject, method);
    }

    public RequestMapping extractAnnotation(final Class<RequestMapping> requestMappingClass) {
        return method.getAnnotation(requestMappingClass);
    }
}
