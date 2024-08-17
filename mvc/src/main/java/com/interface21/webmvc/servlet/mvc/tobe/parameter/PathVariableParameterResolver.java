package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PathVariableParameterResolver implements ParameterResolver {

    private final List<String> urls;

    public PathVariableParameterResolver(String pathVariableUrls) {
        this.urls = Arrays.asList(pathVariableUrls);
    }

    @Override
    public boolean support(Parameter parameter) {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object parseValue(Parameter parameter, HttpServletRequest request,
        HttpServletResponse response) {
        String variableName = parameter.getAnnotation(PathVariable.class)
            .value().isEmpty() ? parameter.getName() : parameter.getAnnotation(PathVariable.class)
            .value();

        String requestUri = request.getRequestURI();

        String variableValues = urls.stream()
            .map(url -> PathPatternUtil.getUriValue(url, requestUri, variableName))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("지원하지 않는 pathVarialbe입니다."));

        return TypeMapper.parse(parameter.getType(), variableValues);
    }

    public void addPathVariableParamater(String url) {
        urls.add(url);
    }
}
