package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

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
        MethodParameters actual = new MethodParameters(method);
        assertThat(actual.getValues()).containsExactly(
                new MethodParameter(String.class, "userId"),
                new MethodParameter(int.class, "userAge")
        );
    }

    @Test
    void request에서_파싱한_값을_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("userId")).thenReturn("jinyoung");
        when(request.getAttribute("userAge")).thenReturn(10);

        MethodParameters methodParameters = new MethodParameters(method);
        List<Object> actual = methodParameters.parseValues(request);
        assertThat(actual).containsExactly("jinyoung", 10);
    }

    private static class TestUserController {

        public ModelAndView createString(String userId, int userAge) {
            return null;
        }
    }
}
