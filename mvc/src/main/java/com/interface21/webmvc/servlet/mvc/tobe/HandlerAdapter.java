package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.interface21.webmvc.servlet.mvc.asis.Controller;
import com.interface21.webmvc.servlet.view.JspView;

public interface HandlerAdapter {

    boolean supports(HttpServletRequest request);

    default void adapt(Object handler, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (handler instanceof Controller) {
            render((Controller) handler, request, response);
        } else if (handler instanceof HandlerExecution) {
            render((HandlerExecution) handler, request, response);
        } else {
            throw new IllegalArgumentException("handler is not supported");
        }
    }

    private static void render(
            HandlerExecution handler, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final var modelAndView = handler.handle(request, response);
        var view = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModel();
        view.render(model, request, response);
    }

    private static void render(
            Controller handler, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final var viewName = handler.execute(request, response);
        var view = new JspView(viewName);
        view.render(Map.of("id", "id"), request, response);
    }
}
