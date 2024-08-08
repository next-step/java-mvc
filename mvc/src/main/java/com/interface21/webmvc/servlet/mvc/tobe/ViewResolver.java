package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ViewResolver {
    boolean accept(ModelAndView modelAndView, HandlerExecution handlerExecution);

    void render(ModelAndView modelAndView,
                HttpServletRequest request,
                HttpServletResponse response) throws Exception;
}
