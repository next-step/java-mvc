package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

public class MethodParameter {

    private int parameterIndex;
    private Parameter parameter;
    private Method method;
    private Class<?> containingClass;

    public MethodParameter(int parameterIndex, Parameter parameter, Method method) {
        this.parameterIndex = parameterIndex;
        this.parameter = parameter;
        this.method = method;
        this.containingClass = method.getDeclaringClass();
    }

    public MethodParameter(int parameterIndex, Method method) {
        this(parameterIndex, null, method);
    }

    public String getParameterName() {
        if (hasNoParameter()) {
            return null;
        }
        return parameter.getName();
    }

    public int getIndex() {
        return parameterIndex;
    }

    public boolean hasNoParameter() {
        return parameterIndex == -1;
    }

    public boolean hasAnnotation() {
        return parameter.getAnnotations().length > 0;
    }

    public boolean isNativeType() {
        return parameter.getType().isPrimitive()
                || parameter.getParameterizedType() == String.class;
    }

    public boolean isType(Class<?> cls) {
        assert cls != null;
        return parameter.getType() == cls;
    }

    public Class<?> getDeclaredType() {
        return parameter.getType();
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
        return parameter.isAnnotationPresent(annotationType);
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof MethodParameter that)) return false;

        return parameterIndex == that.parameterIndex
                && Objects.equals(parameter, that.parameter)
                && Objects.equals(method, that.method)
                && Objects.equals(containingClass, that.containingClass);
    }

    @Override
    public int hashCode() {
        int result = parameterIndex;
        result = 31 * result + Objects.hashCode(parameter);
        result = 31 * result + Objects.hashCode(method);
        result = 31 * result + Objects.hashCode(containingClass);
        return result;
    }

    public Method getMethod() {
        return method;
    }

    public Parameter getParameter() {
        return parameter;
    }
}
