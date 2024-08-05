package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import samples.TestController;
import samples.TestUserController;

class MethodParameterTest {

    @Test
    @DisplayName("MethodParameter를 생성하면 메서드 파라미터에 관련된 정보를 갖는다")
    public void createTest() throws NoSuchMethodException {

        Method method =
                TestUserController.class.getMethod("create_string", String.class, String.class);

        Parameter[] parameters = method.getParameters();

        MethodParameter userId = new MethodParameter(0, parameters[0], method);
        MethodParameter password = new MethodParameter(1, parameters[1], method);

        assertThat(userId.getParameterName()).isEqualTo("userId");
        assertThat(userId.getIndex()).isEqualTo(0);
        assertThat(userId.getDeclaredType()).isEqualTo(String.class);

        assertThat(password.getParameterName()).isEqualTo("password");
        assertThat(password.getIndex()).isEqualTo(1);
        assertThat(password.getDeclaredType()).isEqualTo(String.class);
    }

    @Test
    @DisplayName("동일한 클래스, 메서드, 그리고 같은 타입의 파라미터라면 동등성 비교를 보장한다")
    public void equalsWhenOverloadingMethodEqualTest2() throws NoSuchMethodException {

        Method a = TestUserController.class.getMethod("create_string", String.class, String.class);
        Method b = TestUserController.class.getMethod("create_string", String.class, String.class);

        MethodParameter aa = new MethodParameter(0, a.getParameters()[0], a);
        MethodParameter bb = new MethodParameter(0, b.getParameters()[0], b);

        assertThat(aa).isEqualTo(bb);
    }

    @Test
    @DisplayName("오버로딩된 메서드일 경우 타입이 다른 파라미터에 대해 동등성이 다름을 보장한다")
    public void equalsWhenOverloadingMethodEqualTest() throws NoSuchMethodException {

        Method a = TestUserController.class.getMethod("create_string", String.class, String.class);
        Method b = TestUserController.class.getMethod("create_string", long.class, String.class);

        MethodParameter userIdStringType = new MethodParameter(0, a.getParameters()[0], a);
        MethodParameter userIdLongType = new MethodParameter(0, b.getParameters()[0], b);

        assertThat(userIdStringType).isNotEqualTo(userIdLongType);
    }

    @Test
    @DisplayName("각각 다른 클래스에 있지만 동일한 메서드 시그니처의 파라미터에 대해 동등성이 다름을 보장한다")
    public void equalsWhenSameMethodSignitureEachDifferentClass() throws NoSuchMethodException {

        Method a = TestUserController.class.getMethod("create_string", String.class, String.class);
        Method b = TestController.class.getMethod("create_string", String.class, String.class);

        MethodParameter userIdStringType = new MethodParameter(0, a.getParameters()[0], a);
        MethodParameter userIdStringType2 = new MethodParameter(0, b.getParameters()[0], b);

        assertThat(userIdStringType).isNotEqualTo(userIdStringType2);
    }
}
