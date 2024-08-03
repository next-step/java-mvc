package com.interface21.webmvc.servlet.mvc.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class MethodParameter {
  private final Parameter parameter;
  private final Annotation[] parameterAnnotations;
  private final String parameterName;

  public MethodParameter(Parameter parameter) {
    this.parameter = parameter;
    this.parameterAnnotations = parameter.getAnnotations();
    this.parameterName = parameter.getName();
  }

  public Parameter parameter() {
    return this.parameter;
  }

  public String parameterName() {
    return this.parameterName;
  }

  public Annotation[] parameterAnnotations() {
    return this.parameterAnnotations;
  }

  public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    return parameter.isAnnotationPresent(annotationClass);
  }
}
