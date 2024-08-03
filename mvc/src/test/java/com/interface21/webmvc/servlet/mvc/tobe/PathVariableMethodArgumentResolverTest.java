package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import samples.TestUserController;

class PathVariableMethodArgumentResolverTest {

    @Test
    @DisplayName("파라미터에 PathVariable 어노테이션 타입이 명시되어 있으면 지원한다")
    public void supportTest() throws NoSuchMethodException {

        // given
        var method = TestUserController.class.getMethod("show_pathvariable", long.class);
        var parameters = method.getParameters();

        // when
        var resolver = new PathVariableMethodArgumentResolver();

        // then
        boolean supports = resolver.supports(new MethodParameter(0, parameters[0], method));
        assertTrue(supports);
    }

    @Test
    @DisplayName("메서드 파라미터에 주입할 값을 추출한다")
    public void resolverTest() throws NoSuchMethodException {

        // given
        var method = TestUserController.class.getMethod("show_pathvariable", long.class);
        var parameters = method.getParameters();
        var request = new MockHttpServletRequest();

        // when
        request.setRequestURI("/users/1");
        var resolver = new PathVariableMethodArgumentResolver();

        Object arg =
                resolver.resolveArgument(
                        new MethodParameter(0, parameters[0], method),
                        new ServletRequestResponse(request, null));

        assertThat(arg).isInstanceOf(Long.class);
        assertThat((long) arg).isEqualTo(1);
    }
}
