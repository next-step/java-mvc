package com.interface21.webmvc.servlet.mvc.handler.parameter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interface21.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestUserController;

class PathVariableResolverTest {

    @Test
    @DisplayName("PathVariableResolver는 PathVariable 어노테이션을 지원한다.")
    void support() {
        // given
        PathVariableResolver resolver = new PathVariableResolver();
        Parameter parameterWithPathVariableAnnotation = mock(Parameter.class);
        when(parameterWithPathVariableAnnotation.isAnnotationPresent(PathVariable.class)).thenReturn(true);

        // when
        boolean result = resolver.supportsParameter(parameterWithPathVariableAnnotation);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("PathVariable 어노테이션이 붙은 메서드 인자를 해결할 수 있다.")
    public void testResolveArgument_WithPathVariable() throws Exception {
        // Arrange
        PathVariableResolver resolver = new PathVariableResolver();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Method method = TestUserController.class.getMethod("show_pathvariable", long.class);
        Parameter[] parameters = method.getParameters();
        Parameter parameter = method.getParameters()[0];
        MethodParameter methodParameter = new MethodParameter(method, parameter);

        // Mocking the request URI
        when(request.getRequestURI()).thenReturn("/users/12345");

        // Act
        Object result = resolver.resolveArgument(methodParameter, request, response);

        // Assert
        assertEquals(12345L, result);
    }
}