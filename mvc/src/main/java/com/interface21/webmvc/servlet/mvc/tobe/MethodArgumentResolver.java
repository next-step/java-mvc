package com.interface21.webmvc.servlet.mvc.tobe;

public interface MethodArgumentResolver {

    boolean supports(MethodParameter methodParameter);

    Object resolveArgument(MethodParameter methodParameter, ServletRequestResponse request);
}
