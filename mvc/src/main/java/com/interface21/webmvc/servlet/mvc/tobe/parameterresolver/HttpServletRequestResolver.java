package com.interface21.webmvc.servlet.mvc.tobe.parameterresolver;

import com.interface21.webmvc.servlet.mvc.tobe.HandlerKey;
import com.interface21.webmvc.servlet.mvc.tobe.ParameterResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Parameter;

public class HttpServletRequestResolver implements ParameterResolver {
    @Override
    public boolean accept(HttpServletRequest request, Parameter parameter) {
        return parameter.getType() == HttpServletRequest.class;
    }

    @Override
    public Object resolve(HttpServletRequest request,
                          HttpServletResponse response,
                          Parameter parameter,
                          HandlerKey handlerKey) {
        return request;
    }
}
