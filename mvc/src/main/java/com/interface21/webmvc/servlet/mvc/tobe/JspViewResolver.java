package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.view.JspView;

public class JspViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String viewName) throws Exception {

        return new JspView(viewName + ".jsp");
    }
}
