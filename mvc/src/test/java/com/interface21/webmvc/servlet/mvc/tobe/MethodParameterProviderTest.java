package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import samples.TestUserController;

class MethodParameterProviderTest {

    @Test
    @DisplayName("MethodParameter는 메서드 인자 정보를 가지고 있다")
    public void createMethodParameterTest() throws NoSuchMethodException {

        // given
        Method method =
                TestUserController.class.getMethod("create_string", String.class, String.class);

        // when
        var methodParameters = MethodParameters.from(method);

        // then
        assertThat(methodParameters.size()).isEqualTo(2);
        assertThat(methodParameters.indexOf(0).getParameterName()).isEqualTo("userId");
        assertThat(methodParameters.indexOf(1).getParameterName()).isEqualTo("password");
    }

    @Test
    @DisplayName("Method의 인자가 없어도 MethodParameter 1개가 생성된다")
    public void createMethodParameterTest_whenMethodHasNoArguments() throws NoSuchMethodException {

        // given
        Method method = TestUserController.class.getMethod("show_nothing");

        // when
        var methodParameters = MethodParameters.from(method);

        // then
        assertThat(methodParameters.size()).isEqualTo(1);
        assertFalse(methodParameters.hasParameters());
    }
}
