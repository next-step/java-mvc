package com.interface21.webmvc.servlet.mvc.tobe.parameter.config;

import com.interface21.webmvc.servlet.mvc.tobe.parameter.HttpServletRequestResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameter.HttpServletResponseResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameter.MethodArgumentResolverRegistry;
import com.interface21.webmvc.servlet.mvc.tobe.parameter.ObjectParameterResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameter.ParameterResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameter.ResolverRegistry;
import com.interface21.webmvc.servlet.mvc.tobe.parameter.TypeArgumentResolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public final class ResolverRegistryConfig {

    public static ResolverRegistry REGISTRY;
    private static List<ParameterResolver> RESOLVERS;
    private static ParameterResolver DEFAULT;

    public static ResolverRegistry getResolverRegistry() {
        if (REGISTRY == null) {
            REGISTRY = new MethodArgumentResolverRegistry(getResolvers(), getDefaultResolver());
        }
        return REGISTRY;
    }

    public static List<ParameterResolver> getResolvers() {
        if (RESOLVERS == null) {

            RESOLVERS = new ArrayList<>(Arrays.asList(new HttpServletRequestResolver()
                , new HttpServletResponseResolver(), new TypeArgumentResolver()));
        }

        return RESOLVERS;
    }

    public static ParameterResolver getDefaultResolver() {
        if (DEFAULT == null) {
            DEFAULT = new ObjectParameterResolver();
        }

        return DEFAULT;
    }
}
