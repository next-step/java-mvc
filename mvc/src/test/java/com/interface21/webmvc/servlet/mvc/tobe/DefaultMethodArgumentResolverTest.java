package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import samples.TestUserController;

class DefaultMethodArgumentResolverTest {

    @Test
    @DisplayName("어노테이션이 없으며 기본 타입인 파라미터를 지원한다")
    public void supportTest() throws NoSuchMethodException {

        // given
        final var methodArgumentResolver = new DefaultMethodArgumentResolver();
        final var method =
                TestUserController.class.getMethod("create_string", String.class, String.class);

        // when
        // then
        var parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            var param = parameters[i];
            assertTrue(methodArgumentResolver.supports(new MethodParameter(i, param, method)));
        }
    }

    @Test
    @DisplayName("메서드 파라미터에 주입할 값을 추출한다")
    public void resolveTest() throws NoSuchMethodException {

        // given
        final var methodArgumentResolver = new DefaultMethodArgumentResolver();
        final var request = new MockHttpServletRequest();
        final var method =
                TestUserController.class.getDeclaredMethod(
                        "create_string", String.class, String.class);

        // when
        request.addParameter("userId", "gugu");
        request.addParameter("password", "password");

        // then
        var parameters = method.getParameters();
        var userId = parameters[0];
        assertThat(
                        methodArgumentResolver.resolveArgument(
                                new MethodParameter(0, userId, method),
                                new ServletRequestResponse(request, new MockHttpServletResponse())))
                .isEqualTo("gugu");
    }
}
