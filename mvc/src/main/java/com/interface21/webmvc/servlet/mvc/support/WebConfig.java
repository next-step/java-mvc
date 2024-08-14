package com.interface21.webmvc.servlet.mvc.support;

import com.interface21.webmvc.servlet.mvc.handler.*;

public class WebConfig {

    private WebConfig() {
    }

    public static ArgumentResolvers getArgumentResolvers() {
        ArgumentResolvers argumentResolvers = new ArgumentResolvers();
        argumentResolvers.add(new RequestParamArgumentResolver());
        argumentResolvers.add(new PathVariableArgumentResolver());
        argumentResolvers.add(new RequestBodyArgumentResolver());
        argumentResolvers.add(new ModelAttributeArgumentResolver());
        return argumentResolvers;
    }
}
