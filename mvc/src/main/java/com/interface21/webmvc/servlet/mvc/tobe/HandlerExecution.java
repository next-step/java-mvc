package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public class HandlerExecution {


    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        var modelAndView = new ModelAndView((model, request1, response1) -> {
            // do nothing
        });

        modelAndView.addObject("id", "gugu");
        return modelAndView;
    }
}
