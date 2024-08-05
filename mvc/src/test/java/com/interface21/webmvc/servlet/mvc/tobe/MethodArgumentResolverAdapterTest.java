package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import samples.TestUserController;

class MethodArgumentResolverAdapterTest {

    @Test
    @DisplayName("메서드 파라미터에 주입할 값을 추출한다")
    public void resolveArgumentsTest() throws NoSuchMethodException {

        // given
        var method =
                TestUserController.class.getMethod("create_string", String.class, String.class);
        var methodParameters = MethodParameter.createMethodParameters(method);

        // when
        var request = new MockHttpServletRequest();
        request.addParameter("userId", "gugu");
        request.addParameter("password", "password");

        // then
        Object[] args =
                MethodArgumentResolverAdapter.resolveArguments(
                        methodParameters, new ServletRequestResponse(request, null));

        assertAll(() -> assertEquals("gugu", args[0]), () -> assertEquals("password", args[1]));
    }

    @Test
    @DisplayName("메서드 파라이터에 주입할 값을 추출한다. @PathVariable이 선언된 컨트롤러 테스트")
    public void resolveArgumentsTest2() throws NoSuchMethodException {

        // given
        var method =
                TestUserController.class.getMethod(
                        "show_pathvariable2", String.class, String.class);
        var methodParameters = MethodParameter.createMethodParameters(method);

        // when
        var request = new MockHttpServletRequest();
        request.setRequestURI("/first/second");

        // then
        Object[] args =
                MethodArgumentResolverAdapter.resolveArguments(
                        methodParameters, new ServletRequestResponse(request, null));

        assertThat(args).contains("first", Index.atIndex(0));
        assertThat(args).contains("second", Index.atIndex(1));
    }
}
