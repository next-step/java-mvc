package com.interface21.webmvc.servlet.mvc.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandlerMethodTest {

  @Test
  @DisplayName("생성할 때 메서드의 인자들을 리스트 변수로 할당한다")
  void createHandlerMethodWithMethodParameters() throws NoSuchMethodException {
    final Class<?> handlerMethodTestClass = HandlerMethodTest.class;
    final Method method = handlerMethodTestClass.getMethod("test", String.class, String.class);

    final HandlerMethod handlerMethod = new HandlerMethod(method);

    assertAll(
        () -> assertThat(handlerMethod).isNotNull(),
        () -> assertThat(handlerMethod.methodParameters()).hasSize(2)
    );
  }

  public void test(String name, String age) {
    System.out.println("name:" + name + ", age:" + age);
  }
}