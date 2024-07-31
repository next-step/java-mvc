package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Parameter;

public interface ParameterBinder {
    boolean supports(final Parameter parameter);
    Object bind(final Object arg, final Parameter parameter);
}
