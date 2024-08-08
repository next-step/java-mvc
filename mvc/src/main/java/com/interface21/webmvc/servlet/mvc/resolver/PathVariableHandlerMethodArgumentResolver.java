package com.interface21.webmvc.servlet.mvc.resolver;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.webmvc.servlet.mvc.model.MethodParameter;
import com.interface21.webmvc.servlet.mvc.support.DefaultParameterType;
import com.interface21.webmvc.servlet.mvc.support.PathPatternUtil;
import jakarta.servlet.http.HttpServletRequest;

public class PathVariableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasAnnotation(PathVariable.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, String urlPattern, HttpServletRequest request) {
    final String variableName = parameter.name();
    final String requestUri = request.getRequestURI();

    final String variableValue = PathPatternUtil.getUriValue(urlPattern, requestUri, variableName);

    return DefaultParameterType.convert(parameter.type(), variableValue);
  }
}