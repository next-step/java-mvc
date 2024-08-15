package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.Optional;

public class PathVariableParameterResolver implements ParameterResolver {

    private final String url;

    public PathVariableParameterResolver(String pathVariableUrls) {
        url = pathVariableUrls;
    }

    @Override
    public boolean support(Parameter parameter) {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object parseValue(Parameter parameter, HttpServletRequest request,
        HttpServletResponse response) {
        String variableName = parameter.getAnnotation(PathVariable.class)
            .value();

        if(variableName.isEmpty()){
            variableName = parameter.getName();
        }

        String requestUri = request.getRequestURI();
        String variableValues = PathPatternUtil.getUriValue(url, requestUri, variableName);

        return TypeMapper.parse(parameter.getType(), variableValues);
    }
}
