package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.webmvc.servlet.ModelAndView;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MethodParametersTest {

    @Test
    void 메소드를_받아_파라미터리스트를_생성한다() throws NoSuchMethodException {
        Method method = TestUserController.class
                .getMethod("createString", String.class, int.class);

        MethodParameters actual = new MethodParameters(method);
        assertAll(
                () -> assertThat(actual.getValues()).hasSize(2),
                () -> assertThat(actual.getValues()).containsExactly(
                        new MethodParameter(String.class, "userId"),
                        new MethodParameter(int.class, "userAge")
                )
        );
    }

    private static class TestUserController {

        public ModelAndView createString(String userId, int userAge) {
            return null;
        }
    }
}
