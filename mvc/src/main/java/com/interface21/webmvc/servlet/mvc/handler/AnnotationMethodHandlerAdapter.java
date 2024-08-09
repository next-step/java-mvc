package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.webmvc.servlet.mvc.HandlerAdapter;
import com.interface21.webmvc.servlet.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AnnotationMethodHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (supports(handler)) {
            return ((HandlerExecution) handler).handle(request, response);
        }
        throw new IllegalArgumentException("유효하지 않는 핸들러 : " + handler.getClass().getName());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AnnotationMethodHandlerAdapter;
    }
}