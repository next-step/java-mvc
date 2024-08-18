package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public interface ResolverRegistry {

    Object resolve(Parameter parameter, HttpServletRequest request, HttpServletResponse response);

    ResolverRegistry addResolver(String url);
}
