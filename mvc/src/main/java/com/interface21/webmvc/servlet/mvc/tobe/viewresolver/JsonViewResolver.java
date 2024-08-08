package com.interface21.webmvc.servlet.mvc.tobe.viewresolver;

import com.interface21.web.bind.annotation.ResponseBody;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.mvc.tobe.ViewResolver;
import com.interface21.webmvc.servlet.view.JsonView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JsonViewResolver implements ViewResolver {
    public static final String JSON_VIEW_NAME = "jsonView";

    @Override
    public boolean accept(ModelAndView modelAndView, HandlerExecution handlerExecution) {
        String viewName = modelAndView.getView();
        return (viewName != null && viewName.equals(JSON_VIEW_NAME)) ||
                handlerExecution.hasAnnotated(ResponseBody.class);
    }

    @Override
    public void render(ModelAndView modelAndView,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        View jsonView = new JsonView();
        jsonView.render(modelAndView.getModel(), request, response);
    }
}
