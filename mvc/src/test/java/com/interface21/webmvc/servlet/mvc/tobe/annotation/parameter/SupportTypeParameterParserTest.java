package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SupportTypeParameterParserTest {

    private final Parameter[] parameters = TestUserController.class
            .getMethod("createString", int.class, double.class)
            .getParameters();

    SupportTypeParameterParserTest() throws NoSuchMethodException {
    }

    @Test
    void 지원가능한_타입이면_true를_반환한다() {
        boolean actual = SupportTypeParameterParser.from(parameters[0])
                .accept();
        assertThat(actual).isTrue();
    }

    @Test
    void 지원불가능한_타입이면_false를_반환한다() {
        boolean actual = SupportTypeParameterParser.from(parameters[1])
                .accept();
        assertThat(actual).isFalse();
    }

    @Test
    void 파싱요청은_parameter에서_파싱한값을_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("userId")).thenReturn("123");
        SupportTypeParameterParser parser = SupportTypeParameterParser.from(parameters[0]);

        Object actual = parser.parseValue(request, response);
        assertThat(actual).isEqualTo(123);
    }

    private static class TestUserController {

        public ModelAndView createString(int userId, double userAge) {
            return null;
        }
    }
}
