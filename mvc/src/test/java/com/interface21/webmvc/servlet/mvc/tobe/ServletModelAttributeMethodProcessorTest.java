package com.interface21.webmvc.servlet.mvc.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServletModelAttributeMethodProcessorTest {

    private final ServletModelAttributeMethodProcessor processor = new ServletModelAttributeMethodProcessor();

    @DisplayName("supportsParameter 매서드의 파라미터로 Object 타입의 Parameter 를 받으면 true 를 반환 한다")
    @Test
    public void support() throws Exception {
        // given
        final Parameter parameter = mock(Parameter.class);

        when(parameter.getType()).thenReturn((Class) Object.class);

        // when
        final boolean actual = processor.supportsParameter(parameter);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("supportsParameter 매서드의 파라미터로 기본 자료형 타입의 Parameter 를 받으면 true 를 반환 한다")
    @Test
    public void notSupport() throws Exception {
        // given
        final Parameter parameter = mock(Parameter.class);

        when(parameter.getType()).thenReturn((Class) int.class);

        // when
        final boolean actual = processor.supportsParameter(parameter);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("웹 요청의 파라미터를 이용해 객체를 만들어 반환 한다")
    @Test
    public void resolveParameter() throws Exception {
        // given
        final Parameter parameter = mock(Parameter.class);
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();

        when(parameter.getType()).thenReturn((Class) TestUser.class);
        request.addParameter("name", "홍길동");
        request.addParameter("age", "20");

        // when
        final Object actual = processor.resolveArgument(parameter, new ServletWebRequest(request, response));

        // then
        assertThat(actual).isInstanceOf(TestUser.class)
                .extracting("name", "age")
                .contains("홍길동", 20);
    }

    private static class TestUser {
        private String name;
        private int age;

        private TestUser(final String name, final int age) {
            this.name = name;
            this.age = age;
        }
    }
}
