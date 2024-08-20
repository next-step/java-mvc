package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;
import com.interface21.webmvc.servlet.mvc.tobe.support.TypeCheckUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.IntStream;

public class PathVariableResolver implements ArgumentResolver {

  @Override
  public boolean supportsParameter(Parameter parameter) {
    return parameter.isAnnotationPresent(PathVariable.class);
  }

  @Override
  public Object resolve(HttpServletRequest request, HttpServletResponse response,
      Parameter parameter, Method method) {
    PathVariable annotation = parameter.getAnnotation(PathVariable.class);
    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
    String variableName = annotation.value().isEmpty() ? parameter.getName() : annotation.value();
    String requestUri = request.getRequestURI();
    String urlPattern = requestMapping.value();

    String value = PathPatternUtil.getUriValue(urlPattern, requestUri, variableName);
    if (value == null) {
      throw new IllegalArgumentException("Path variable을 찾지 못했습니다. : " + variableName);
    }

    return TypeCheckUtil.convertStringToTargetType(value, parameter.getType());
  }
}
