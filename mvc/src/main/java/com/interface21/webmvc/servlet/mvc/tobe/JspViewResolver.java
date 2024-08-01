package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.view.JspView;

public class JspViewResolver implements ViewResolver {

    private static final String DEFAULT_SUFFIX = ".jsp";

    @Override
    public View resolveViewName(String viewName) {
        return new JspView(viewName);
    }

    @Override
    public boolean supports(String viewName) {
        return viewName.endsWith(DEFAULT_SUFFIX);
    }
}
