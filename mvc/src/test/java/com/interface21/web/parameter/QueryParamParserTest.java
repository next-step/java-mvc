package com.interface21.web.parameter;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class QueryParamParserTest {

    private final QueryParamParser parser = new QueryParamParser();

    @Test
    void cannot_parse_nomatch_name() throws NoSuchMethodException {
        var parameter = TestQueryParamClass.class.getDeclaredMethod("cannot_parse_nomatch_name", String.class).getParameters()[0];
        final HttpServletRequest request = mock(HttpServletRequest.class);
        doReturn("1").when(request).getParameter("wrong");

        assertThat(parser.parse(null, parameter, request)).isNull();
    }


    @Test
    void parsed_by_param_name() throws NoSuchMethodException {
        var parameter = TestQueryParamClass.class.getDeclaredMethod("parsed_by_param_name", long.class).getParameters()[0];
        final HttpServletRequest request = mock(HttpServletRequest.class);
        doReturn("1").when(request).getParameter("count");

        assertThat(parser.parse(null, parameter, request)).isEqualTo(1L);
    }
}

class TestQueryParamClass {

    public void cannot_parse_nomatch_name(String name) {

    }

    public void parsed_by_param_name(long count) {

    }
}