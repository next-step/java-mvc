package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.View;

public interface ViewResolver {

    View resolveViewName(String viewName) throws Exception;
}
