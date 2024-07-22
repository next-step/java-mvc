package com.interface21.webmvc.servlet.mvc.tobe;

public class FakeMethodParameter extends MethodParameter {
    private final Class<?> parameterType;
    private final String parameterName;
    private final String pathVariableValue;

    public FakeMethodParameter(final Class<?> parameterType, final String parameterName) {
        this(parameterType, parameterName, "");
    }

    public FakeMethodParameter(final Class<?> parameterType, final String parameterName, final String pathVariableValue) {
        super(null);
        this.parameterType = parameterType;
        this.parameterName = parameterName;
        this.pathVariableValue = pathVariableValue;
    }

    @Override
    public Class<?> getParameterType() {
        return this.parameterType;
    }

    @Override
    public String getParameterName() {
        return this.parameterName;
    }

    @Override
    public boolean isPathVariable() {
        return pathVariableValue != null && !pathVariableValue.isEmpty();
    }

    @Override
    public String getPathVariableValue() {
        return pathVariableValue;
    }
}
