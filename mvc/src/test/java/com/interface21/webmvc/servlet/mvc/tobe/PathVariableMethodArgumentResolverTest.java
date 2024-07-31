package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PathVariableMethodArgumentResolverTest {

    private final PathVariableMethodArgumentResolver resolver = new PathVariableMethodArgumentResolver();

    @DisplayName("support 파라미터로 PathVariable 애너테이션이 붙은 파라미터가 넘어 오면 true 를 반환 한다")
    @Test
    public void supportParameter() throws Exception {
        // given
        final Parameter parameter = mock(Parameter.class);

        when(parameter.isAnnotationPresent(PathVariable.class)).thenReturn(true);

        // when
        final boolean actual = resolver.supportsParameter(new MethodParameter(mock(Method.class), parameter));

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("support 파라미터로 PathVariable 애너테이션이 없는 파라미터가 넘어 오면 false 를 반환 한다")
    @Test
    public void notSupportParameter() throws Exception {
        // given
        final Parameter parameter = mock(Parameter.class);

        when(parameter.isAnnotationPresent(PathVariable.class)).thenReturn(false);

        // when
        final boolean actual = resolver.supportsParameter(new MethodParameter(mock(Method.class), parameter));

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("PathVariable 애너테이션의 value() 를 이용해 uri path 매핑 된 파라미터를 반환 한다")
    @Test
    public void resolveParameterWithAnnotationValue() throws Exception {
        // given
        final Method method = mock(Method.class);
        final Parameter parameter = mock(Parameter.class);
        final PathVariable pathVariable = mock(PathVariable.class);
        final RequestMapping requestMapping = mock(RequestMapping.class);
        final MockHttpServletRequest request = new MockHttpServletRequest();

        request.setRequestURI("/api/gugu");
        when(parameter.getName()).thenReturn("id");
        when(parameter.getAnnotation(PathVariable.class)).thenReturn(pathVariable);
        when(pathVariable.value()).thenReturn("userId");
        when(method.getAnnotation(RequestMapping.class)).thenReturn(requestMapping);
        when(requestMapping.value()).thenReturn("/api/{userId}");
        when(parameter.getType()).thenReturn((Class) String.class);

        // when
        final Object actual = resolver.resolveArgument(new MethodParameter(method, parameter), new ServletWebRequest(request, new MockHttpServletResponse()));

        // then
        assertThat(actual).isEqualTo("gugu");
    }

    @DisplayName("파라미터 이름을 이용해 uri path 매핑 된 파라미터를 반환 한다")
    @Test
    public void resolveParameterWithParameterName() throws Exception {
        // given
        final Method method = mock(Method.class);
        final Parameter parameter = mock(Parameter.class);
        final PathVariable pathVariable = mock(PathVariable.class);
        final RequestMapping requestMapping = mock(RequestMapping.class);
        final MockHttpServletRequest request = new MockHttpServletRequest();

        request.setRequestURI("/api/gugu");
        when(parameter.getName()).thenReturn("userId");
        when(parameter.getAnnotation(PathVariable.class)).thenReturn(pathVariable);
        when(pathVariable.value()).thenReturn("");
        when(method.getAnnotation(RequestMapping.class)).thenReturn(requestMapping);
        when(requestMapping.value()).thenReturn("/api/{userId}");
        when(parameter.getType()).thenReturn((Class) String.class);

        // when
        final Object actual = resolver.resolveArgument(new MethodParameter(method, parameter), new ServletWebRequest(request, new MockHttpServletResponse()));

        // then
        assertThat(actual).isEqualTo("gugu");
    }
}
