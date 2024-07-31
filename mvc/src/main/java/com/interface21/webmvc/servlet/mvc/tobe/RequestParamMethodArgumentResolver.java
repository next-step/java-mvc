package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Parameter;

public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final boolean useDefaultResolution;
    private final ParameterBinders binders = new ParameterBinders();

    public RequestParamMethodArgumentResolver(final boolean useDefaultResolution) {
        this.useDefaultResolution = useDefaultResolution;
    }

    @Override
    public boolean supportsParameter(final Parameter parameter) {
        return useDefaultResolution;
    }

    @Override
    public Object resolveArgument(final Parameter parameter, final ServletWebRequest webRequest) throws Exception {
        final Object arg = resolveName(parameter.getName(), webRequest);

        final ParameterBinder binder = binders.getBinder(parameter);

        return binder.bind(arg, parameter);
    }

    private String resolveName(final String parameterName, final ServletWebRequest webRequest) {
        return webRequest.getRequest().getParameter(parameterName);
    }
}
