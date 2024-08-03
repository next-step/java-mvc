package com.interface21.webmvc.servlet.mvc.resolver;

import com.interface21.webmvc.servlet.mvc.model.MethodParameter;

public interface HandlerMethodArgumentResolver {

  boolean supportsParameter(MethodParameter parameter);

  Object resolveArgument(MethodParameter parameter);

}
