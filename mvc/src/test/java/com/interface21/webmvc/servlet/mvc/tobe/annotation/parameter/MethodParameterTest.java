package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;
import java.util.Objects;

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
        MethodParameter actual = MethodParameter.of("", parameter);
        assertAll(
                () -> assertThat(actual.getParameterType()).isEqualTo(String.class),
                () -> assertThat(actual.getParameterName()).isEqualTo("userId")
        );
    }

    @Test
    void 타입이_HttpServletRequest인_경우_request를_그대로_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object actual = new MethodParameter(HttpServletRequest.class, "request", new PathParameter("", false, "")).parseValue(request, response);
        assertThat(actual).isEqualTo(request);
    }

    @Test
    void 타입이_HttpServletResponse인_경우_response를_그대로_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object actual = new MethodParameter(HttpServletResponse.class, "response", new PathParameter("", false, "")).parseValue(request, response);
        assertThat(actual).isEqualTo(response);
    }

    @Test
    void PathVariable이_존재하면_파싱한_값으로_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getRequestURI()).thenReturn("/users/123");

        Object actual = new MethodParameter(int.class, "userId", new PathParameter("/users/{userId}", true, "")).parseValue(request, response);
        assertThat(actual).isEqualTo(123);
    }

    @Test
    void HttpServletRequest에서_parameterType에_해당하는_값을_파싱한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("userAge")).thenReturn("10");

        Object actual = new MethodParameter(int.class, "userAge", new PathParameter("", false, "")).parseValue(request, response);
        assertAll(
                () -> assertThat(actual.getClass()).isEqualTo(Integer.class),
                () -> assertThat(actual).isEqualTo(10)
        );
    }

    @Test
    void 타입이_객체인_경우_각_parameter를_파싱하여_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("userId")).thenReturn("jinyoung");
        when(request.getParameter("password")).thenReturn("1234");
        when(request.getParameter("age")).thenReturn("10");

        Object actual = new MethodParameter(TestUser.class, "testUser", new PathParameter("", false, "")).parseValue(request, response);
        assertThat(actual).isEqualTo(new TestUser("jinyoung", "1234", 10));
    }

    @Test
    void equals() {
        MethodParameter methodParameter = new MethodParameter(String.class, "name", new PathParameter("", false, ""));
        assertThat(methodParameter).isEqualTo(new MethodParameter(String.class, "name", new PathParameter("", false, "")));
    }

    private static class TestUserController {

        public ModelAndView createString(String userId) {
            return null;
        }
    }

    public static class TestUser {
        private String userId;
        private String password;
        private int age;

        public TestUser() {
        }

        public TestUser(String userId, String password, int age) {
            this.userId = userId;
            this.password = password;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestUser testUser = (TestUser) o;
            return age == testUser.age && Objects.equals(userId, testUser.userId) && Objects.equals(password, testUser.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, password, age);
        }
    }
}
