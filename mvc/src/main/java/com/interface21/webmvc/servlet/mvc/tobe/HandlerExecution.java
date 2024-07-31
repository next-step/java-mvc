package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class HandlerExecution {

    private final Object bean;
    private final Method method;
    private final HandlerMethodArgumentResolverComposite resolvers;

    public HandlerExecution(final Object bean, final Method method, final HandlerMethodArgumentResolverComposite resolvers) {
        this.bean = bean;
        this.method = method;
        this.resolvers = resolvers;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Object[] arguments = createArguments(method.getParameters(), request, response);
        return (ModelAndView) method.invoke(bean, arguments);
    }

    private Object[] createArguments(final Parameter[] parameters, final HttpServletRequest request, final HttpServletResponse response) {
        return Arrays.stream(parameters)
                .filter(resolvers::supportsParameter)
                .map(parameter -> resolvers.resolveArgument(parameter, new ServletWebRequest(request, response)))
                .toArray();
    }
}
