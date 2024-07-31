package com.interface21.webmvc.servlet.mvc.tobe;

public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(final MethodParameter parameter);

    Object resolveArgument(final MethodParameter parameter, final ServletWebRequest webRequest) throws Exception;
}
