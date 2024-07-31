package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodParameter {

    private final Method method;
    private final Parameter parameter;

    public MethodParameter(final Method method, final Parameter parameter) {
        this.method = method;
        this.parameter = parameter;
    }

    public String getName() {
        return parameter.getName();
    }

    public Class<?> getType() {
        return parameter.getType();
    }

    public Parameter getInternalParameter() {
        return this.parameter;
    }

    public boolean isAnnotationPresent(final Class<? extends Annotation> annotationClass) {
        return parameter.isAnnotationPresent(annotationClass);
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return parameter.getAnnotation(annotationClass);
    }

    public RequestMapping getRequestMappingAnnotation() {
        return this.method.getAnnotation(RequestMapping.class);
    }
}
