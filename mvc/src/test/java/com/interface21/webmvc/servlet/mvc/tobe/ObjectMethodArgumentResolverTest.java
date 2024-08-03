package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import samples.TestUser;
import samples.TestUserController;

@DisplayName("ObjectMethodArgumentResolver 는")
class ObjectMethodArgumentResolverTest {

    @Test
    @DisplayName("메서드 파라미터가 Pojo 타입인 경우 지원한다")
    public void supportTest() throws NoSuchMethodException {

        // given
        final var method = TestUserController.class.getMethod("create_javabean", TestUser.class);
        final var parameter = method.getParameters()[0];

        // when
        final var resolver = new ObjectMethodArgumentResolver();

        // then
        assertTrue(resolver.supports(new MethodParameter(0, parameter, method)));
    }

    @Test
    @DisplayName("메서드 파라미터 타입이 Pojo인 경우 Pojo 객체로 바인딩 한다")
    public void resolveTest() throws NoSuchMethodException {

        // given
        final var method = TestUserController.class.getMethod("create_javabean", TestUser.class);
        final var parameter = method.getParameters()[0];

        final var request = new MockHttpServletRequest();
        request.addParameter("userId", "1");
        request.addParameter("password", "password");
        request.addParameter("age", "1");

        // when
        final var resolver = new ObjectMethodArgumentResolver();

        // then
        Object arg =
                resolver.resolveArgument(
                        new MethodParameter(0, parameter, method),
                        new ServletRequestResponse(request, null));

        assertThat(arg).isInstanceOf(TestUser.class);
        assertThat(arg).isEqualTo(new TestUser("1", "password", 1));
    }
}
