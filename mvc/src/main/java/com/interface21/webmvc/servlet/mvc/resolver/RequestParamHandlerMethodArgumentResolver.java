package com.interface21.webmvc.servlet.mvc.resolver;

import com.interface21.web.bind.annotation.RequestParam;
import com.interface21.webmvc.servlet.mvc.model.MethodParameter;
import com.interface21.webmvc.servlet.mvc.support.DefaultParameterType;
import jakarta.servlet.http.HttpServletRequest;

public class RequestParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasAnnotation(RequestParam.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, String urlPattern, HttpServletRequest request) {
    final String parameterName = parameter.name();
    final String value = request.getParameter(parameterName);

    return DefaultParameterType.convert(parameter.type(), value);
  }
}
