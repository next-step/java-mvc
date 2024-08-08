package com.interface21.webmvc.servlet;

import com.interface21.webmvc.servlet.mvc.model.MethodParameter;

public class MethodArgumentResolutionException extends RuntimeException{

  public MethodArgumentResolutionException(MethodParameter parameter) {
    super(getMethodParameterMessage(parameter));
  }

  private static String getMethodParameterMessage(MethodParameter parameter) {
    int var10000 = parameter.index();
    return "Could not resolve method parameter at index " + var10000;
  }
}
