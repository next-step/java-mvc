package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.ArrayList;
import java.util.List;

public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {

    private final List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();

    public void addResolver(final HandlerMethodArgumentResolver resolver) {
        resolvers.add(resolver);
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return resolvers.stream()
                .anyMatch(resolver -> resolver.supportsParameter(parameter));
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ServletWebRequest webRequest) {
        return resolvers.stream()
                .filter(resolver -> resolver.supportsParameter(parameter))
                .findFirst()
                .map(resolver -> resolveArgumentInternal(resolver, parameter, webRequest))
                .orElseThrow(() -> new ArgumentNotResolvedException("Unable to resolve Argument = [" + parameter.getName() + "]"));
    }

    private Object resolveArgumentInternal(final HandlerMethodArgumentResolver resolver, final MethodParameter parameter, final ServletWebRequest webRequest) throws ArgumentNotResolvedException {
        try {
            return resolver.resolveArgument(parameter, webRequest);
        } catch (Exception e) {
            throw new ArgumentNotResolvedException("Unable to resolve Argument = [" + parameter.getName() + "]", e);
        }
    }

}
