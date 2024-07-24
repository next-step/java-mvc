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
    private final HandlerMethodArgumentResolver argumentResolver = new ServletHandlerMethodArgumentResolver();

    public HandlerExecution(final Object bean, final Method method) {
        this.bean = bean;
        this.method = method;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Object[] arguments = createArguments(method.getParameters(), request, response);
        return (ModelAndView) method.invoke(bean, arguments);
    }

    private Object[] createArguments(final Parameter[] parameters, final HttpServletRequest request, final HttpServletResponse response) {
        return Arrays.stream(parameters)
                .filter(argumentResolver::supportsParameter)
                .map(parameter -> {
                    try {
                        return argumentResolver.resolveArgument(parameter, new ServletWebRequest(request, response));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).toArray();
    }
}
