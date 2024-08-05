package com.interface21.webmvc.servlet.mvc.tobe.argumentresolver;

import com.interface21.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ObjectArgumentResolverTest {

    private final TestClass testClass = new TestClass();

    @Test
    void supportsParameterTest() {
        // given
        final ObjectArgumentResolver resolver = new ObjectArgumentResolver();

        // when
        final boolean result = resolver.supportsParameter(testClass.getClass().getDeclaredMethods()[0].getParameters()[0]);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void resolveArgumentTest() throws NoSuchMethodException {
        // given
        final ObjectArgumentResolver resolver = new ObjectArgumentResolver();
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        given(httpServletRequest.getParameter("name")).willReturn("test");
        final Method method = testClass.getClass().getDeclaredMethod("handle", TestParameterClass.class);

        // when
        final Object result = resolver.resolveArgument(method.getParameters()[0], method, httpServletRequest, null);
        final TestParameterClass resultClass = (TestParameterClass) result;

        // then
        assertThat(resultClass.getClass()).isEqualTo(TestParameterClass.class);
        assertThat(resultClass.getName()).isEqualTo("test");
    }

    static class TestClass {

        @RequestMapping("/hello")
        void handle(final TestParameterClass object) {
        }
    }

    static class TestParameterClass {

        private String name;

        public TestParameterClass() {
        }

        public TestParameterClass(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
