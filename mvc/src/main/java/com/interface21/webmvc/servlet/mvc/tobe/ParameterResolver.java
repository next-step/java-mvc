package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Parameter;

public interface ParameterResolver {
    boolean accept(HttpServletRequest request, Parameter parameter);

    Object resolve(HttpServletRequest request,
                   HttpServletResponse response,
                   Parameter parameter,
                   HandlerKey handlerKey);
}
