package com.interface21.webmvc.servlet.mvc.tobe.method;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PathVariableArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        return methodParameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        RequestMapping requestMapping = methodParameter.getMethodAnnotation(RequestMapping.class);
        String uriPattern = requestMapping.value();
        String pathVariableName = getPathVariableName(methodParameter);
        String requestUri = httpServletRequest.getRequestURI();
        String pathVariableValue = PathPatternUtil.getUriValue(uriPattern, requestUri, pathVariableName);

        return SimpleArgument.convert(methodParameter.getParameterType(), pathVariableValue);
    }

    private String getPathVariableName(MethodParameter methodParameter) {
        PathVariable pathVariable = methodParameter.getParameterAnnotation(PathVariable.class);
        String pathVariableName = pathVariable.value();

        if (pathVariableName == null || pathVariableName.isEmpty()) {
            return methodParameter.getParameterName();
        }

        return pathVariableName;
    }
}
