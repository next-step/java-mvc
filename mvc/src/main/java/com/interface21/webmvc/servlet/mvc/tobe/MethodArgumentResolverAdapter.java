package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

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
            MethodParameters methodParameters, ServletRequestResponse request) {

        if (!methodParameters.hasParameters()) {
            return new Object[0];
        }


        return IntStream.range(0, methodParameters.size())
                .mapToObj(methodParameters::indexOf)
                .map(
                        methodParameter -> {
                            var resolver = findResolver(methodParameter);
                            return resolver.resolveArgument(methodParameter, request);
                        })
                .toArray(Object[]::new);
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
