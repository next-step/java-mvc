package com.interface21.webmvc.servlet.mvc.handler.parameter;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.mvc.support.PathPatternUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class PathVariableResolver implements ArgumentResolver {

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Method method = methodParameter.getMethod();
        Parameter parameter = methodParameter.getParameter();
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String mappingPattern = requestMapping.value();

        String requestURI = request.getRequestURI();
        if (!PathPatternUtil.isUrlMatch(mappingPattern, requestURI)) {
            throw new IllegalArgumentException("PathVariable not found");
        }
        Map<String, String> uriVariables = PathPatternUtil.getUriVariables(mappingPattern, requestURI);
        String parameterName = parameter.getName();
        return BasicArgumentType.convert(uriVariables.get(parameterName), parameter.getType());
    }
}
