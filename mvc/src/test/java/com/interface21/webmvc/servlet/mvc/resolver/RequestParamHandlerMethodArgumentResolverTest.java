package com.interface21.webmvc.servlet.mvc.resolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.web.bind.annotation.RequestParam;
import com.interface21.webmvc.servlet.mvc.model.MethodParameter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class RequestParamHandlerMethodArgumentResolverTest {
  private final static String URL_PATTERN = "/users";

  private final HandlerMethodArgumentResolver target = new RequestParamHandlerMethodArgumentResolver();

  @Test
  @DisplayName("@RequestParam 어노테이션이 붙은 인자만 처리한다")
  void supportParameterTest() throws NoSuchMethodException {
    final Method method = TestClass.class.getDeclaredMethod("search", String.class);
    final Parameter parameter = method.getParameters()[0];
    final MethodParameter methodParameter = new MethodParameter(0, parameter);

    boolean result = target.supportsParameter(methodParameter);

    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("@RequestParam 어노테이션이 붙은 파라미터의 값을 가져온다")
  void resolveArgumentTest() throws Exception {
    final Method method = TestClass.class.getDeclaredMethod("search", String.class);
    final Parameter parameter = method.getParameters()[0];
    final MethodParameter methodParameter = new MethodParameter(0, parameter);
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter("name", "heedoitdox");

    final Object result = target.resolveArgument(methodParameter,null, request);

    assertThat(result).isEqualTo("heedoitdox");
  }

  static class TestClass {
    @RequestMapping(value = URL_PATTERN, method = RequestMethod.GET)
    void search(@RequestParam String name) {

    }
  }
}