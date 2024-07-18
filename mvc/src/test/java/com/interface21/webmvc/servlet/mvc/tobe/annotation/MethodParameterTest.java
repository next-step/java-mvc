package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.webmvc.servlet.ModelAndView;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    private static class TestUserController {

        public ModelAndView createString(String userId) {
            return null;
        }
    }
}
