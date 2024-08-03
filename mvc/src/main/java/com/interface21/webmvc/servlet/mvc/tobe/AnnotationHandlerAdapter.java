package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.tobe.method.ArgumentResolvers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AnnotationHandlerAdapter implements HandlerAdapter {

    private final ArgumentResolvers argumentResolver;

    public AnnotationHandlerAdapter(ArgumentResolvers argumentResolvers) {
        this.argumentResolver = argumentResolvers;
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return ((HandlerExecution) handler).handle(argumentResolver, request, response);
    }
}
