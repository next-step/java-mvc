package com.interface21.webmvc.servlet.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JsonView;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandlerExecutionTest {

  @Test
  @DisplayName("가변인자를 받아서 메서드를 invoke 한다")
  void methodInvokeByVariableArgumentsTest() throws Exception {
    final AnnotationControllerClass clazz = new AnnotationControllerClass(TestClass.class);
    final Method method = clazz.getRequestMappingMethod()[0];
    final HandlerExecution handlerExecution = new HandlerExecution(clazz, method);
    final Object[] args = new Object[] {"heedoitdox"};

    final ModelAndView result = handlerExecution.handle(args);

    assertThat(result.getModel().get("name")).isEqualTo(args[0]);
  }

  static class TestClass {
    @RequestMapping
    ModelAndView foo(String name) {
      final ModelAndView modelAndView = new ModelAndView(new JsonView());
      modelAndView.addObject("name", name);
      return modelAndView;
    }
  }
}