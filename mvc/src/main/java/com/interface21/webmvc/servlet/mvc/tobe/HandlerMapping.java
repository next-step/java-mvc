package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;

public interface HandlerMapping extends HandlerAdapter {
    Object getHandler(HttpServletRequest request);
}
