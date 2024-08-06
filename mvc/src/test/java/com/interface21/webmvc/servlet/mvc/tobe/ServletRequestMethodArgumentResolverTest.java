package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import samples.TestController;

@DisplayName("ServletRequestMethodArgumentResolver는")
class ServletRequestMethodArgumentResolverTest {

    @Test
    @DisplayName("Servlet request, response 타입을 지원한다")
    public void supportTest() throws NoSuchMethodException {

        // given
        var method =
                TestController.class.getMethod(
                        "findUserId", HttpServletRequest.class, HttpServletResponse.class);
        final var methodParameter = new MethodParameter(0, method.getParameters()[0], method);

        // when
        final var resolver = new ServletRequestMethodArgumentResolver();

        // then
        assertTrue(resolver.supports(methodParameter));
    }

    @Test
    @DisplayName("HttpServletRequest를 반환한다")
    public void resolveTest() throws NoSuchMethodException {

        // given
        final var method =
                TestController.class.getMethod(
                        "findUserId", HttpServletRequest.class, HttpServletResponse.class);
        final var methodParameter = new MethodParameter(0, method.getParameters()[0], method);

        // when
        final var resolver = new ServletRequestMethodArgumentResolver();

        var actual =
                resolver.resolveArgument(
                        methodParameter,
                        new ServletRequestResponse(
                                new MockHttpServletRequest(), new MockHttpServletResponse()));
        assertThat(actual).isInstanceOf(HttpServletRequest.class);
    }
}
