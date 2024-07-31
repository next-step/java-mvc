package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestParamMethodArgumentResolverTest {

    @DisplayName("디폴트로 사용 되는 리졸버는 support() true 를 반환 한다")
    @Test
    public void supportDefaultResolution() throws Exception {
        // given
        final RequestParamMethodArgumentResolver resolver = new RequestParamMethodArgumentResolver(true);

        // when
        final boolean actual = resolver.supportsParameter(mock(Parameter.class));

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("디폴트로 사용 되지 않는 리졸버는 support() false 를 반환 한다")
    @Test
    public void supportNotDefaultResolution() throws Exception {
        // given
        final RequestParamMethodArgumentResolver resolver = new RequestParamMethodArgumentResolver(false);

        // when
        final boolean actual = resolver.supportsParameter(mock(Parameter.class));

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("컨트롤러 메서드의 파라미터 이름과 일치하는 웹 요청 파라미터를 찾아 반환 한다")
    @Test
    public void resolveArgument() throws Exception {
        // given
        final RequestParamMethodArgumentResolver resolver = new RequestParamMethodArgumentResolver(false);

        final Parameter parameter = mock(Parameter.class);
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final ServletWebRequest webRequest = new ServletWebRequest(request, response);

        final String parameterKey = "name";
        final String parameterValue = "kim";
        request.addParameter(parameterKey, parameterValue);

        when(parameter.getName()).thenReturn(parameterKey);
        when(parameter.getType()).thenReturn((Class) String.class);

        // when
        final Object actual = resolver.resolveArgument(parameter, webRequest);

        // then
        assertThat(actual).isEqualTo("kim");
    }
}
