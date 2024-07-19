package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MethodParametersTest {

    private final Method method = TestUserController.class
            .getMethod("createString", String.class, int.class);

    MethodParametersTest() throws NoSuchMethodException {
    }

    @Test
    void 메소드를_받아_파라미터리스트를_생성한다() {
        MethodParameters actual = MethodParameters.of("", method);
        assertThat(actual.getValues()).hasSize(2);
    }

    @Test
    void request에서_파싱한_값을_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("userId")).thenReturn("jinyoung");
        when(request.getParameter("userAge")).thenReturn("10");

        MethodParameters methodParameters = MethodParameters.of("", method);
        Object[] actual = methodParameters.parseValues(request, response);
        assertThat(actual).containsExactly("jinyoung", 10);
    }

    private static class TestUserController {

        public ModelAndView createString(String userId, int userAge) {
            return null;
        }
    }
}
