package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public interface ParameterResolver {

    boolean support(Parameter parameter);

    Object parseValue(HttpServletRequest request, HttpServletResponse response);
}
