package com.interface21.webmvc.servlet.mvc.tobe.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodParameter {

    private final Method method;
    private final Parameter parameter;

    public MethodParameter(Method method, Parameter parameter) {
        this.method = method;
        this.parameter = parameter;
    }

    public Method getMethod() {
        return method;
    }

    public String getParameterName() {
        return parameter.getName();
    }

    public Class<?> getParameterType() {
        return parameter.getType();
    }

    public boolean isAssignableFrom(Class<?> clazz) {
        return getParameterType().isAssignableFrom(clazz);
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
        return parameter.isAnnotationPresent(annotation);
    }

    public <T extends Annotation> T getParameterAnnotation(Class<T> annotation) {
        return parameter.getAnnotation(annotation);
    }

    public <T extends Annotation> T getMethodAnnotation(Class<T> annotation) {
        return method.getAnnotation(annotation);
    }

    public boolean isEmptyAnnotation() {
        return parameter.getAnnotations().length == 0;
    }
}
