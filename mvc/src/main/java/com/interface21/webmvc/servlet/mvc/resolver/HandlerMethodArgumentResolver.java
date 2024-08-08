package com.interface21.webmvc.servlet.mvc.resolver;

import com.interface21.webmvc.servlet.mvc.model.MethodParameter;
import jakarta.servlet.http.HttpServletRequest;

public interface HandlerMethodArgumentResolver {

  boolean supportsParameter(MethodParameter parameter);

  Object resolveArgument(MethodParameter parameter, String urlPattern, HttpServletRequest request);
}
