package com.interface21.webmvc.servlet.mvc.tobe.argumentresolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface HandlerMethodArgumentResolver {

    boolean supportsParameter(Parameter parameter);

    Object resolveArgument(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response);

}
