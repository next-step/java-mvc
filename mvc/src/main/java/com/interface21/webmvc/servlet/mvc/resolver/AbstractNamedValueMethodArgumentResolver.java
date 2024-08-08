package com.interface21.webmvc.servlet.mvc.resolver;

import com.interface21.webmvc.servlet.mvc.model.MethodParameter;

public abstract class AbstractNamedValueMethodArgumentResolver implements HandlerMethodArgumentResolver{

  public Object resolveArgument(MethodParameter parameter) {
    return "abstract Name Value Method";
  }
}
