package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.mvc.support.PathPatternUtil;
import com.interface21.webmvc.servlet.mvc.support.TypeConversionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class PathVariableArgumentResolver implements ArgumentResolver {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolve(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String uriPattern = requestMapping.value();
        String requestURI = request.getRequestURI();

        String pathVariableName = getPathVariableName(parameter);
        String uriValue = PathPatternUtil.getUriValue(uriPattern, requestURI, pathVariableName);
        return TypeConversionUtil.convertStringToTargetType(uriValue, parameter.getType());
    }

    private String getPathVariableName(Parameter parameter) {
        PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
        if (pathVariable.name().isBlank()) {
            return parameter.getName();
        }
        return pathVariable.name();
    }
}
