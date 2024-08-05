package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;

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
        MethodParameter[] methodParameters = MethodParameter.createMethodParameters(method);

        // then
        assertThat(methodParameters).hasSize(2);
        assertThat(methodParameters[0].getParameterName()).isEqualTo("userId");
        assertThat(methodParameters[1].getParameterName()).isEqualTo("password");
    }

    @Test
    @DisplayName("Method의 인자가 없을 경우 MethodParameter 1개가 생성되며 파라미터 선언 순서를 의미하는 인덱스는 -1이다")
    public void createMethodParameterTest_whenMethodHasNoArguments() throws NoSuchMethodException {

        // given
        Method method = TestUserController.class.getMethod("show_nothing");

        // when
        MethodParameter[] methodParameters = MethodParameter.createMethodParameters(method);

        // then
        assertThat(methodParameters).hasSize(1);
        assertThat(methodParameters[0].getIndex()).isEqualTo(-1);
    }
}
