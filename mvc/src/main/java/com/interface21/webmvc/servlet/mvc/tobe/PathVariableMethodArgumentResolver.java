package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

public class PathVariableMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final ParameterBinders binders = new ParameterBinders();

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ServletWebRequest webRequest) throws Exception {
        String resolvedName = parameter.getName();

        final String annotationValue = parameter.getAnnotation(PathVariable.class).value();

        if (!annotationValue.isBlank()) {
            resolvedName = annotationValue;
        }

        final String requestUrl = ((HttpServletRequest) webRequest.getRequest()).getRequestURI();

        final String arg = PathPatternUtil.getUriValue(parameter.getRequestMappingAnnotation().value(), requestUrl, resolvedName);

        final Parameter internalParameter = parameter.getInternalParameter();
        final ParameterBinder binder = binders.getBinder(internalParameter);

        return binder.bind(arg, internalParameter);
    }
}
