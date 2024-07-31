package com.interface21.webmvc.servlet.mvc.handler.parameter;

import java.util.List;

public class ArgumentResolverConfiguration {

    private static final List<ArgumentResolver> resolvers = List.of(new HttpServletRequestResolver(),
                                                                    new HttpServletResponseResolver(),
                                                                    new PathVariableResolver(),
                                                                    new BasicArgumentTypeResolver(),
                                                                    new ObjectResolver());

    public ArgumentResolverConfiguration() {
        throw new UnsupportedOperationException("This is a configuration class and should not be instantiated");
    }

    public static List<ArgumentResolver> getResolvers() {
        return resolvers;
    }
}
