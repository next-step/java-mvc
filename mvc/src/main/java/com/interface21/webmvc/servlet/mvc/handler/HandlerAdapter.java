package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    boolean accept(Object handler);

    ModelAndView handle(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
