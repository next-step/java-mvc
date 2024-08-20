package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestParam;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentResolverTest {

  @Test
  void HttpServletRequest요청시_그대로_반환한다() throws NoSuchMethodException {
    // Given
    HttpServletRequestResolver resolver = new HttpServletRequestResolver();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    Parameter parameter = getParameterByName("request");

    // When
    boolean supports = resolver.supportsParameter(parameter);
    Object resolved = resolver.resolve(request, response, parameter,null);

    // Then
    assertTrue(supports);
    assertEquals(request, resolved);
  }

  @Test
  void HttpServletResponse요청시_그대로_반환한다() throws NoSuchMethodException {
    // Given
    HttpServletResponseResolver resolver = new HttpServletResponseResolver();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    Parameter parameter = getParameterByName("response");

    // When
    boolean supports = resolver.supportsParameter(parameter);
    Object resolved = resolver.resolve(request, response, parameter, null);

    // Then
    assertTrue(supports);
    assertEquals(response, resolved);
  }

  @Test
  void PathVariable어노테이션이_있으면_URL에서_값을_추출한다() throws NoSuchMethodException {
    // Given
    PathVariableResolver resolver = new PathVariableResolver();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    Parameter parameter = getParameterByName("id");
    request.setRequestURI("/users/12356");

    // When
    boolean supports = resolver.supportsParameter(parameter);
    Object resolved = resolver.resolve(request, response, parameter, null);

    // Then
    assertTrue(supports);
    assertEquals(123456L, resolved);
  }

  @Test
  void RequestParam어노테이션이_있으면_파라미터에서_값을_추출한다() throws NoSuchMethodException {
    // Given
    RequestParamResolver resolver = new RequestParamResolver();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    Parameter parameter = getParameterByName("name");
    request.setParameter("name", "박정호");

    // When
    boolean supports = resolver.supportsParameter(parameter);
    Object resolved = resolver.resolve(request, response, parameter, null);

    // Then
    assertTrue(supports);
    assertEquals("박정호", resolved);
  }

  @Test
  void 필수_요청파라미터_누락시_예외를_던진다() throws NoSuchMethodException {
    // Given
    RequestParamResolver resolver = new RequestParamResolver();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    Parameter parameter = getParameterByName("name");

    // When & Then
    assertTrue(resolver.supportsParameter(parameter));
    assertThrows(IllegalArgumentException.class, () -> resolver.resolve(request, response, parameter, null));
  }

  private Parameter getParameterByName(String name) throws NoSuchMethodException {
    return Arrays.stream(TestController.class.getDeclaredMethod("testMethod", Long.class, String.class, HttpServletRequest.class, HttpServletResponse.class).getParameters())
        .filter(p -> p.getName().equals(name))
        .findFirst()
        .orElseThrow(() -> new NoSuchMethodException("파라미터를 찾지 못했습니다."));
  }

  static class TestController {
    public void testMethod(@PathVariable Long id,
        @RequestParam String name,
        HttpServletRequest request,
        HttpServletResponse response) {
    }
  }
}
