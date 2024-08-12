package com.interface21.webmvc.servlet.mvc.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.model.MethodParameter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class DefaultHandlerMethodArgumentResolverTest {

  @Test
  @DisplayName("메서드의 아무 어노테이션도 붙지않은 (기본) 인자를 매핑한다")
  void resolveArgumentTest() throws Exception {
    final HandlerMethodArgumentResolver resolver = new DefaultHandlerMethodArgumentResolver();
    final Method method = TestClass.class.getDeclaredMethod("post", String.class);
    final Parameter parameter = method.getParameters()[0];
    final MethodParameter methodParameter = new MethodParameter(0, parameter);
    final MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter("name", "heedoitdox");

    final Object result = resolver.resolveArgument(methodParameter, null, request);

    assertThat(result).isEqualTo("heedoitdox");
    assertThat(result.getClass()).isEqualTo(String.class);
  }

  static class TestClass {
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    void post(String name) {

    }
  }
}