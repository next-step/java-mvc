package com.interface21.webmvc.servlet.mvc.tobe.argumentresolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ArgumentResolversTest {

    @Test
    void argumentResolverTest() {
        // given
        final ArgumentResolvers argumentResolvers = ArgumentResolvers.getInstance();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Method method = mock(Method.class);
        final Parameter parameter = mock(Parameter.class);

        given(method.getParameters()).willReturn(new Parameter[]{parameter});
        given(parameter.getName()).willReturn("userId");
        given(parameter.getType()).willReturn((Class) String.class);
        given(request.getParameter("userId")).willReturn("test");

        // when
        final Object[] result = argumentResolvers.resolveArguments(method, request, response);

        // then
        assertThat(result).containsExactly("test");
    }

}
