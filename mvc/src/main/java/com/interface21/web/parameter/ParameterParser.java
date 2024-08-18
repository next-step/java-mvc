package com.interface21.web.parameter;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface ParameterParser {
    Object parse(final Method method, final Parameter parameter, final HttpServletRequest request);

}
