package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Parameter;

public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final boolean useDefaultResolution;
    private final ParameterBinders binders = new ParameterBinders();

    public RequestParamMethodArgumentResolver(final boolean useDefaultResolution) {
        this.useDefaultResolution = useDefaultResolution;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return useDefaultResolution;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ServletWebRequest webRequest) throws Exception {
        final Object arg = resolveName(parameter.getName(), webRequest);

        final Parameter internalParameter = parameter.getInternalParameter();
        final ParameterBinder binder = binders.getBinder(internalParameter);

        return binder.bind(arg, internalParameter);
    }

    private String resolveName(final String parameterName, final ServletWebRequest webRequest) {
        return webRequest.getRequest().getParameter(parameterName);
    }
}
