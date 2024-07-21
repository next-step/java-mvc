package com.interface21.webmvc.servlet;

import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestHandlerAdapter implements HandlerAdapter {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, HandlerExecution handler) throws Exception {
        var modelAndView = handler.handle(request, response);
        modelAndView.getView().render(modelAndView.getModel(), request, response);
    }
}
