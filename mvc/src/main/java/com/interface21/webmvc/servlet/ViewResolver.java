package com.interface21.webmvc.servlet;

import com.interface21.webmvc.servlet.view.JsonView;
import com.interface21.webmvc.servlet.view.JspView;

public class ViewResolver {
    public static View resolveViewName(String viewName) {
        if (viewName == null) {
            return new JspView("redirect:/404.jsp");
        }

        if (viewName.endsWith(".jsp")) {
            return new JspView(viewName);
        }
        return new JspView("redirect:/404.jsp");
    }

    public static View resolveJsonView() {
        return new JsonView();
    }
}
