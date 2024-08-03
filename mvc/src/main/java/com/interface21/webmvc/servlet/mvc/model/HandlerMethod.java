package com.interface21.webmvc.servlet.mvc.model;

import java.lang.reflect.Method;
import java.util.Arrays;

public class HandlerMethod {
  private final Method method;
  private final MethodParameter[] methodParameters;

  public HandlerMethod(Method method) {
    this.method = method;
    this.methodParameters = Arrays.stream(method.getParameters()).map(MethodParameter::new).toArray(MethodParameter[]::new);
  }

  public Method method() {
    return this.method;
  }

  public MethodParameter[] methodParameters() {
    return this.methodParameters;
  }
}
