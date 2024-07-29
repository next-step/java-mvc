package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    boolean supports(HttpServletRequest request);

    default void adapt(Object handler, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        render((HandlerExecution) handler, request, response);
    }

    private static void render(
            HandlerExecution handler, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final var modelAndView = handler.handle(request, response);
        var view = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModel();
        view.render(model, request, response);
    }
}
