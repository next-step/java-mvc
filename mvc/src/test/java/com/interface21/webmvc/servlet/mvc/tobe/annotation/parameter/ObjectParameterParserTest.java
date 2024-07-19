package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ObjectParameterParserTest {

    @Test
    void 지원가능여부를_호출할_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new ObjectParameterParser(TestUser.class).accept())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Object parser는 지원가능여부를 호출할 수 없습니다.");
    }

    @Test
    void 파싱요청은_parameter에서_파싱한값을_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("userId")).thenReturn("jinyoung");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("age")).thenReturn("10");
        ObjectParameterParser parser = new ObjectParameterParser(TestUser.class);

        Object actual = parser.parseValue(request, response);
        assertThat(actual).isEqualTo(new TestUser("jinyoung", "123", 10));
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
