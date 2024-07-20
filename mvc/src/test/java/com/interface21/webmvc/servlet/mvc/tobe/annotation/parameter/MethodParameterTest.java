package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import com.interface21.web.bind.annotation.PathVariable;
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

    private final Parameter[] parameters = TestUserController.class
            .getMethod("createString", String.class, HttpServletRequest.class, HttpServletResponse.class, int.class, int.class, TestUser.class)
            .getParameters();

    MethodParameterTest() throws NoSuchMethodException {
    }

    @Test
    void Parameter를_받아_Parser를_저장한다() {
        MethodParameter actual = MethodParameter.of("", parameters[0]);
        assertAll(
                () -> assertThat(actual.getMethodParameterParsers()).hasSize(4),
                () -> assertThat(actual.getDefaultParameterParser()).isInstanceOf(ObjectParameterParser.class)
        );
    }

    @Test
    void 타입이_HttpServletRequest인_경우_request를_그대로_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object actual = MethodParameter.of("", parameters[1]).parseValue(request, response);
        assertThat(actual).isEqualTo(request);
    }

    @Test
    void 타입이_HttpServletResponse인_경우_response를_그대로_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object actual = MethodParameter.of("", parameters[2]).parseValue(request, response);
        assertThat(actual).isEqualTo(response);
    }

    @Test
    void PathVariable이_존재하면_파싱한_값으로_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getRequestURI()).thenReturn("/users/123");

        Object actual = MethodParameter.of("/users/{userId}", parameters[3]).parseValue(request, response);
        assertThat(actual).isEqualTo(123);
    }

    @Test
    void HttpServletRequest에서_parameterType에_해당하는_값을_파싱한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("userAge")).thenReturn("10");

        Object actual = MethodParameter.of("", parameters[4]).parseValue(request, response);
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

        Object actual = MethodParameter.of("", parameters[5]).parseValue(request, response);
        assertThat(actual).isEqualTo(new TestUser("jinyoung", "1234", 10));
    }

    private static class TestUserController {

        public ModelAndView createString(
                String userName,
                HttpServletRequest request,
                HttpServletResponse response,
                @PathVariable int userId,
                int userAge,
                TestUser testUser
        ) {
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
