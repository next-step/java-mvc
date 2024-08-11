package com.interface21.webmvc.servlet.mvc.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.model.MethodParameter;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PathVariableHandlerMethodArgumentResolverTest {
  private final HandlerMethodArgumentResolver target = new PathVariableHandlerMethodArgumentResolver();

  private final static String URL_PATTERN = "/users/{id}";

  @Test
  @DisplayName("@PathVariable 어노테이션이 붙은 인자만 처리한다")
  void supportParameterTest() throws NoSuchMethodException {
    final Method method = TestClass.class.getDeclaredMethod("get", String.class);
    final Parameter parameter = method.getParameters()[0];
    final MethodParameter methodParameter = new MethodParameter(0, parameter);

    boolean result = target.supportsParameter(methodParameter);

    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("@PathVariable 어노테이션이 붙은 파라미터의 값을 가져온다.")
  void resolveArgumentTest() throws Exception {
    final Method method = TestClass.class.getDeclaredMethod("get", String.class);
    final Parameter parameter = method.getParameters()[0];
    final MethodParameter methodParameter = new MethodParameter(0, parameter);
    final HttpServletRequest request = mock(HttpServletRequest.class);

    given(request.getRequestURI()).willReturn("/users/3");

    final Object result = target.resolveArgument(methodParameter, URL_PATTERN, request);

    assertThat(result).isEqualTo("3");
  }

  static class TestClass {
    @RequestMapping(value = URL_PATTERN, method = RequestMethod.GET)
    void get(@PathVariable("id") String id) {

    }
  }
}
