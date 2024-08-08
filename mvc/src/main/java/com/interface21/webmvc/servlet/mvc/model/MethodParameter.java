package com.interface21.webmvc.servlet.mvc.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;

public class MethodParameter {
  private final int index;
  private final Parameter parameter;
  private final Executable executable;
  private final Annotation[] parameterAnnotations;
  private final String parameterName;

  public MethodParameter(int index, Parameter parameter) {
    this.index = index;
    this.parameter = parameter;
    this.executable = parameter.getDeclaringExecutable();
    this.parameterAnnotations = parameter.getAnnotations();
    this.parameterName = parameter.getName();
  }

  public int index() {
    return index;
  }

  public Parameter parameter() {
    return this.parameter;
  }

  public Executable executable() {
    return this.executable;
  }

  public String name() {
    return this.parameterName;
  }

  public Annotation[] parameterAnnotations() {
    return this.parameterAnnotations;
  }

  public Class<?> type() {
    return this.parameter.getType();
  }

  public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
    return parameter.isAnnotationPresent(annotationClass);
  }
}
