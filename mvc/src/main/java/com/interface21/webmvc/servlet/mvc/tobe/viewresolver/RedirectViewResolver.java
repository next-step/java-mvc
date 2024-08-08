package com.interface21.webmvc.servlet.mvc.tobe.viewresolver;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.mvc.tobe.ViewResolver;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RedirectViewResolver implements ViewResolver {
    public static final String REDIRECT_VIEW_PREFIX = "redirect:";

    @Override
    public boolean accept(ModelAndView modelAndView, HandlerExecution handlerExecution) {
        String viewName = modelAndView.getView();

        return viewName != null && viewName.startsWith(REDIRECT_VIEW_PREFIX + "/");
    }

    @Override
    public void render(ModelAndView modelAndView,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        View jspView = new JspView(modelAndView.getView());

        jspView.render(modelAndView.getModel(), request, response);
    }
}
