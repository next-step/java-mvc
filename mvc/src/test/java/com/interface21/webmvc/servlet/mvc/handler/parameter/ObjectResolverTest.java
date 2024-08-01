package com.interface21.webmvc.servlet.mvc.handler.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import samples.TestUser;
import samples.TestUserController;

class ObjectResolverTest {

    @Test
    @DisplayName("supportsParameter는 BasicArgument가 아닌 커스텀 클래스 타입을 지원한다.")
    void supports() throws NoSuchMethodException {
        // given
        ObjectResolver resolver = new ObjectResolver();
        Method method = TestUserController.class.getMethod("create_javabean", TestUser.class);
        Parameter parameter = method.getParameters()[0];

        // when
        boolean result = resolver.supportsParameter(parameter);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("resolveArgument는 BasicArgument가 아닌 커스텀 클래스 타입의 메서드 인자를 해결할 수 있다.")
    void testResolveArgument_WithCustomClass() throws Exception {
        // given
        ObjectResolver resolver = new ObjectResolver();
        Method method = TestUserController.class.getMethod("create_javabean", TestUser.class);
        MethodParameter parameter = new MethodParameter(method, method.getParameters()[0]);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("userId", "javajigi");
        request.addParameter("password", "pass");
        request.addParameter("age", "20");

        // when
        Object result = resolver.resolveArgument(parameter, request, null);
        TestUser resultObject = (TestUser) result;

        // then
        assertAll(
            () -> assertThat(result).isNotNull(),
            () -> assertThat(result).isInstanceOf(TestUser.class),
            () -> assertThat(resultObject.getUserId()).isEqualTo("javajigi"),
            () -> assertThat(resultObject.getPassword()).isEqualTo("pass"),
            () -> assertThat(resultObject.getAge()).isEqualTo(20)
        );
    }
}