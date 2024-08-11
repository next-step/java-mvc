package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.parameter.MethodArgumentResolverRegistry;
import com.interface21.webmvc.servlet.mvc.tobe.parameter.ResolverRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class HandlerExecution {

    private final Object handler;
    private final Method method;
    private final ResolverRegistry registry;
    private Map<Method, Object[]> args = new HashMap<>();

    public HandlerExecution(Object handler, Method method, ResolverRegistry registry) {
        this.handler = handler;
        this.method = method;
        this.registry = registry;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response)
        throws Exception {

        return (ModelAndView) method.invoke(handler, request, response);
    }

    private Object[] resolveArguments(HttpServletRequest request, HttpServletResponse response) {
        if (args.isEmpty()) {
            initializeArgs(request, response);
        }

        return args.get(method);
    }

    private void initializeArgs(HttpServletRequest request, HttpServletResponse response) {
        Object[] objects = Arrays.stream(method.getParameters())
            .map(parameter -> registry.resolve(parameter, request, response))
                .toArray();
        args.put(method, objects);
    }
}
