package com.interface21.webmvc.servlet.mvc.tobe.viewresolver;

import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.tobe.ViewResolver;
import com.interface21.webmvc.servlet.view.JspView;

public class RedirectViewResolver implements ViewResolver {
    public static final String REDIRECT_VIEW_PREFIX = "redirect:";

    @Override
    public View resolveView(String viewName) {
        if (viewName.startsWith(REDIRECT_VIEW_PREFIX + "/")) {
            return new JspView(viewName);
        }
        return null;
    }
}
