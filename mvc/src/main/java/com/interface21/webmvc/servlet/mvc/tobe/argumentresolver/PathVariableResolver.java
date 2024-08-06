package com.interface21.webmvc.servlet.mvc.tobe.argumentresolver;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class PathVariableResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.getAnnotatedType().isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolveArgument(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response) {
        final RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        final String requestMappingPath = requestMapping.value();
        final String requestPath = request.getRequestURI();
        final String pathVariableName = parameter.getAnnotation(PathVariable.class)
                .value();

        final String pathVariableValue = PathPatternUtil.getUriValue(requestMappingPath, requestPath, pathVariableName);

        if (pathVariableValue == null) {
            throw new IllegalArgumentException("No such path variable : " + pathVariableName);
        }

        return ParameterClassType.parse(parameter.getType(), pathVariableValue);
    }
}
