package com.interface21.webmvc.servlet.mvc.tobe;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import samples.TestUserController;

class MethodParameterTest {

    // 특히 파라미터 / 메서드 / 클래스 각각이 갖고 다를 경우에 관해서,
    // MethodParameter 의 행동이 어떻게 달라지는지 테스트 해주시면 좋을 것 같습니다.

    @Test
    @DisplayName("MethodParameter를 생성하면 메서드 파라미터에 관련된 속성을 갖는다")
    public void createTest() throws NoSuchMethodException {

        Method method =
                TestUserController.class.getMethod("create_string", String.class, String.class);

        var methodParameters = MethodParameters.from(method);

        assertEquals(2, methodParameters.size());
    }
}
