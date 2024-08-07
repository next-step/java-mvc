package com.interface21.webmvc.servlet.mvc.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.web.bind.annotation.PathVariable;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MethodParameterTest {

  @Test
  @DisplayName("메서드 인자에 특정 annotation 이 존재하면 true 를 반환한다")
  void hasAnnotation_returnTrue() {
    final Class<?> test = MethodParameterTest.class;
    final Method[] methods = test.getDeclaredMethods();

    MethodParameter methodParameter = new MethodParameter(0, methods[0].getParameters()[0]);

    assertThat(methodParameter.hasAnnotation(PathVariable.class)).isTrue();
  }

  public void test(@PathVariable Long id) {
    System.out.println(id);
  }
}