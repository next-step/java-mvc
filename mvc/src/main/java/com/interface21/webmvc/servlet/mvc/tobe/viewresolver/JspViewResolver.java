package com.interface21.webmvc.servlet.mvc.tobe.viewresolver;

import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.mvc.tobe.ViewResolver;
import com.interface21.webmvc.servlet.view.JspView;

public class JspViewResolver implements ViewResolver {

    public static final String JSP_FILE_NAME_EXTENSION = ".jsp";

    @Override
    public View resolveView(String viewName, HandlerExecution handlerExecution) {
        if (viewName != null && viewName.endsWith(JSP_FILE_NAME_EXTENSION)) {
            return new JspView(viewName);
        }
        return null;
    }
}
