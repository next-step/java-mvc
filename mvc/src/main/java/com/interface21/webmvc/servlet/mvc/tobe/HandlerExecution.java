package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;


public class HandlerExecution {

    private final Method method;

    public HandlerExecution(Method method) {
        this.method = method;
    }


    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        var modelAndView = new ModelAndView((model, request1, response1) -> {
            // do nothing
        });

        modelAndView.addObject("id", "gugu");
        return modelAndView;
    }
}
