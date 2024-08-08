package com.interface21.webmvc.servlet.mvc.tobe.parameterresolver;

import com.interface21.core.util.ConversionUtil;
import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerKey;
import com.interface21.webmvc.servlet.mvc.tobe.ParameterResolver;
import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Parameter;

public class PathVariableParameterResolver implements ParameterResolver {
    @Override
    public boolean accept(HttpServletRequest request, Parameter parameter) {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolve(HttpServletRequest request,
                          HttpServletResponse response,
                          Parameter parameter,
                          HandlerKey handlerKey) {
        String pathParamValue = getPathParamValue(request, parameter, handlerKey);
        Class<?> parameterType = parameter.getType();

        return ConversionUtil.boxPrimitiveValue(pathParamValue, parameterType);
    }

    private String getPathParamValue(HttpServletRequest request, Parameter parameter, HandlerKey handlerKey) {
        return PathPatternUtil.getUriValue(handlerKey.getUrl(), request.getRequestURI(), parameter.getName());
    }
}
