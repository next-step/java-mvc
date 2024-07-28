package com.interface21.webmvc.servlet.mvc.tobe.viewresolver;

import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.tobe.ViewResolver;
import com.interface21.webmvc.servlet.view.JsonView;

public class JsonViewResolver implements ViewResolver {
    public static final String JSON_VIEW_NAME = "jsonView";

    @Override
    public View resolveView(String viewName) {
        if (viewName.equals(JSON_VIEW_NAME)) {
            return new JsonView();
        }
        return null;
    }
}
