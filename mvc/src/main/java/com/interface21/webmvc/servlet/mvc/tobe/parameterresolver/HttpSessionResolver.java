package com.interface21.webmvc.servlet.mvc.tobe.parameterresolver;

import com.interface21.webmvc.servlet.mvc.tobe.ParameterResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.lang.reflect.Parameter;

public class HttpSessionResolver implements ParameterResolver {
    @Override
    public boolean accept(HttpServletRequest request, Parameter parameter) {
        return parameter.getType() == HttpSession.class;
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Parameter parameter) {
        return request.getSession();
    }
}
