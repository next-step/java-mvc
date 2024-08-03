package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.Map;

import com.interface21.core.TypeResolver;
import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;

public class PathVariableMethodArgumentResolver implements MethodArgumentResolver {

    @Override
    public boolean supports(MethodParameter methodParameter) {
        return methodParameter.hasAnnotation(PathVariable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ServletRequestResponse request) {

        var method = methodParameter.getMethod();
        var requestMapping = method.getAnnotation(RequestMapping.class);

        Map<String, String> uriVariables =
                PathPatternUtil.getUriVariables(
                        requestMapping.value(), request.getRequest().getRequestURI());

        return TypeResolver.resolve(
                methodParameter.getParameter(),
                uriVariables.get(methodParameter.getParameterName()));
    }
}
