package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MethodArgumentResolverAdapter {

    private static final List<MethodArgumentResolver> argumentResolvers =
            List.of(
                    new ServletRequestMethodArgumentResolver(),
                    new DefaultMethodArgumentResolver(),
                    new ObjectMethodArgumentResolver(),
                    new PathVariableMethodArgumentResolver());
    private static final Map<MethodParameter, MethodArgumentResolver> CACHE =
            new ConcurrentHashMap<>();

    private MethodArgumentResolverAdapter() {}

    public static Object[] resolveArguments(
            MethodParameter[] methodParameters, ServletRequestResponse request) {

        assert methodParameters != null;

        if (methodParameters.length == 1 && methodParameters[0].hasNoParameter()) {
            return new Object[0];
        }

        var args = new Object[methodParameters.length];
        for (int i = 0; i < methodParameters.length; i++) {
            var methodParameter = methodParameters[i];
            var resolver = MethodArgumentResolverAdapter.findResolver(methodParameter);
            args[i] = resolver.resolveArgument(methodParameter, request);
        }
        return args;
    }

    private static MethodArgumentResolver findResolver(MethodParameter parameter) {
        if (CACHE.containsKey(parameter)) {
            return CACHE.get(parameter);
        }

        for (MethodArgumentResolver argumentResolver : argumentResolvers) {
            if (argumentResolver.supports(parameter)) {
                CACHE.put(parameter, argumentResolver);
                return argumentResolver;
            }
        }

        throw new IllegalStateException(
                "No suitable argument resolver found for parameter: " + parameter);
    }
}
