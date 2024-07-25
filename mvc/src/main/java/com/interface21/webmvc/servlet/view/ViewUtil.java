package com.interface21.webmvc.servlet.view;

import com.interface21.webmvc.servlet.View;

public class ViewUtil {
    public static View createViewByViewName(String viewName) {
        if (viewName.endsWith(".jsp")) {
            return new JspView(viewName);
        }

        return createDefaultview(viewName);
    }

    private static View createDefaultview(String viewName) {
        return new JspView(viewName);
    }
}
