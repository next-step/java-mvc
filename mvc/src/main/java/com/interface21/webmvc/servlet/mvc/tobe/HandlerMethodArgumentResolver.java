package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Parameter;

public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(final Parameter parameter);

    Object resolveArgument(final Parameter parameter, final ServletWebRequest webRequest) throws Exception;
}
