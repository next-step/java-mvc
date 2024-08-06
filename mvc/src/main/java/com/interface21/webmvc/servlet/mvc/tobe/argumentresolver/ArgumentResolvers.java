package com.interface21.webmvc.servlet.mvc.tobe.argumentresolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class ArgumentResolvers {

    private static final List<HandlerMethodArgumentResolver> DEFAULT_RESOLVERS = List.of(
            new SimpleObjectArgumentResolver(),
            new HttpServletRequestResolver(),
            new HttpServletResponseResolver(),
            new PathVariableResolver()
    );
    private final List<HandlerMethodArgumentResolver> resolvers;

    public ArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        this.resolvers = resolvers;
    }

    public static ArgumentResolvers getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public Object[] resolveArguments(final Method method, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        final Parameter[] parameters = method.getParameters();

        return Arrays.stream(parameters)
                .map(parameter -> resolveArgument(parameter, method, httpServletRequest, httpServletResponse))
                .toArray();
    }

    private Object resolveArgument(final Parameter parameter, final Method method, final HttpServletRequest request, final HttpServletResponse response) {
        return resolvers.stream()
                .filter(resolver -> resolver.supportsParameter(parameter))
                .findFirst()
                .map(resolver -> resolver.resolveArgument(parameter, method, request, response))
                .orElse(null);
    }

    private static class SingletonHelper {
        private static final ArgumentResolvers INSTANCE = new ArgumentResolvers(DEFAULT_RESOLVERS);
    }
}
