package com.interface21.webmvc.servlet.mvc.tobe.viewresolver;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.mvc.tobe.ViewResolver;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JspViewResolver implements ViewResolver {
    public static final String JSP_FILE_NAME_EXTENSION = ".jsp";

    @Override
    public boolean accept(ModelAndView modelAndView, HandlerExecution handlerExecution) {
        String viewName = modelAndView.getView();

        return viewName != null && viewName.endsWith(JSP_FILE_NAME_EXTENSION);
    }

    @Override
    public void render(ModelAndView modelAndView,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        View jspView = new JspView(modelAndView.getView());

        jspView.render(modelAndView.getModel(), request, response);
    }
}
