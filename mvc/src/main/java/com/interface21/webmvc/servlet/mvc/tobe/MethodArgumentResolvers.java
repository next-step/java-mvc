package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;


public class MethodArgumentResolvers {
    private final List<ArgumentResolver> resolvers;

    private MethodArgumentResolvers(List<ArgumentResolver> resolvers) {
        this.resolvers = resolvers;
    }

    public static MethodArgumentResolvers create(String urlPattern, Method method) {
        List<ArgumentResolver> resolvers = Arrays.stream(method.getParameters())
                .map(param -> chooseResolverForParameter(urlPattern, param))
                .toList();
        return new MethodArgumentResolvers(resolvers);
    }

    private static ArgumentResolver chooseResolverForParameter(String urlPattern, Parameter parameter) {
        return List.of(
                        new HttpServletRequestResolver(),
                        new HttpServletResponseResolver(),
                        new PathVariableResolver(urlPattern),
                        new RequestParamResolver()
                ).stream()
                .filter(resolver -> resolver.supportsParameter(parameter))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하는 resolver가 없습니다. : " + parameter.getName()));
    }

    public Object[] resolveArguments(HttpServletRequest request, HttpServletResponse response,Parameter parameter) {
        return resolvers.stream()
                .map(resolver -> resolver.resolve(request, response, parameter))
                .toArray();
    }
}