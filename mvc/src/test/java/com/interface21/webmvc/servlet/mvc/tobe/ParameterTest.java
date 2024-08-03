package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParameterTest {

    @Test
    @DisplayName("Parameter#getName()을 통해 파라미터 이름을 가져올 수 있다")
    public void getNameTest() throws NoSuchMethodException {

        // given
        var method = MockClass.class.getMethod("test", String.class, int.class);
        var parameter = method.getParameters()[0];

        // when & then
        assertThat(parameter.getName()).isEqualTo("a");
    }

    @Test
    @DisplayName("Parameter#getAnnotations()을 통해 파라미터에 부여된 어노테이션을 가져올 수 있다")
    public void getAnnotationsTest() throws NoSuchMethodException {

        // given
        var method = MockClass.class.getMethod("test", String.class, int.class);
        var a = method.getParameters()[0];

        assertThat(a.getAnnotations()).hasSize(0);
    }

    class MockClass {

        public void test(String a, int b) {}
    }
}
