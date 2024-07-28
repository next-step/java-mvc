package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestHandlerAdapter implements HandlerAdapter {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final var modelAndView = ((HandlerExecution) handler).handle(request, response);
        modelAndView.getView().render(modelAndView.getModel(), request, response);
    }
}
