package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MethodParameterTest {

    @Test
    void Parameter를_받아_type과_이름을_저장한다() throws NoSuchMethodException {
        Parameter parameter = TestUserController.class
                .getMethod("createString", String.class)
                .getParameters()[0];
        MethodParameter actual = new MethodParameter(parameter);
        assertAll(
                () -> assertThat(actual.getParameterType()).isEqualTo(String.class),
                () -> assertThat(actual.getParameterName()).isEqualTo("userId")
        );
    }

    @Test
    void HttpServletRequest에서_parameterType에_해당하는_값을_파싱한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("userAge")).thenReturn(10);

        Object actual = new MethodParameter(int.class, "userAge").parseValue(request);
        assertAll(
                () -> assertThat(actual.getClass()).isEqualTo(Integer.class),
                () -> assertThat(actual).isEqualTo(10)
        );
    }

    private static class TestUserController {

        public ModelAndView createString(String userId) {
            return null;
        }
    }
}
