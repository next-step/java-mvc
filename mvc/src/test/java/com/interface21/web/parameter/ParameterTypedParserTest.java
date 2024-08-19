package com.interface21.web.parameter;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import samples.TestUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ParameterTypedParserTest {

    private final ParameterTypedParser parser = new ParameterTypedParser(TestUser.class);


    @Test
    void cannot_parse_nomatch_type() throws NoSuchMethodException {
        var parameter = TestTypedParamClass.class.getDeclaredMethod("cannot_parse_nomatch_type", String.class).getParameters()[0];

        assertThat(parser.parse(null, parameter, null)).isNull();
    }

    @Test
    void parse_by_requestParam() throws NoSuchMethodException {
        var parameter = TestTypedParamClass.class.getDeclaredMethod("parse_by_requestParam", TestUser.class).getParameters()[0];
        var request = mock(HttpServletRequest.class);
        when(request.getParameter("userId")).thenReturn("woo-yu");
        when(request.getParameter("password")).thenReturn("pass");
        when(request.getParameter("age")).thenReturn("1234");

        var result = (TestUser) parser.parse(null, parameter, request);

        assertThat(result.getUserId()).isEqualTo("woo-yu");
        assertThat(result.getPassword()).isEqualTo("pass");
        assertThat(result.getAge()).isEqualTo(1234);

    }


}

class TestTypedParamClass {

    public void cannot_parse_nomatch_type(String name) {

    }

    public void parse_by_requestParam(TestUser user) {

    }
}