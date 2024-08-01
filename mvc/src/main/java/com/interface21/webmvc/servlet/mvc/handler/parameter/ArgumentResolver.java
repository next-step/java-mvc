package com.interface21.webmvc.servlet.mvc.handler.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public interface ArgumentResolver {

    boolean supportsParameter(Parameter parameter);

    Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
