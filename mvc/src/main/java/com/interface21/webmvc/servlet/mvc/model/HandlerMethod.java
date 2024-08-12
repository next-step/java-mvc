package com.interface21.webmvc.servlet.mvc.model;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class HandlerMethod {
  private final Method method;
  private final MethodParameter[] methodParameters;

  public HandlerMethod(Method method) {
    this.method = method;
    this.methodParameters = getMethodParameters(method);
  }

  public Method method() {
    return this.method;
  }

  public MethodParameter[] methodParameters() {
    return this.methodParameters;
  }

  private static MethodParameter[] getMethodParameters(Method method) {
    Parameter[] parameters = method.getParameters();
    MethodParameter[] methodParameters = new MethodParameter[parameters.length];
    for(int i = 0; i < parameters.length; i++) {
      methodParameters[i] = new MethodParameter(i, parameters[i]);
    }
    return methodParameters;
  }
}
