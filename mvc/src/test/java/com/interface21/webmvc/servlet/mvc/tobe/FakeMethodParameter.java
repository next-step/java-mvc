package com.interface21.webmvc.servlet.mvc.tobe;

public class FakeMethodParameter extends MethodParameter {
    private final Class<?> parameterType;
    private final String parameterName;

    public FakeMethodParameter(final Class<?> parameterType, final String parameterName) {
        super(null);
        this.parameterType = parameterType;
        this.parameterName = parameterName;
    }

    @Override
    public Class<?> getParameterType() {
        return this.parameterType;
    }

    @Override
    public String getParameterName() {
        return this.parameterName;
    }
}
