package com.interface21.web.parameter;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;
import java.net.http.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PathVariableParserTest {

    private final PathVariableParser parser = new PathVariableParser();

    @Test
    void cannot_parse_without_pathVariableParameter() throws NoSuchMethodException {
        var method = TestPathVariableClass.class.getDeclaredMethod("cannot_parse_without_pathVariableParameter", String.class);
        var parameter = method.getParameters()[0];

        var result = parser.parse(method, parameter, null);

        assertThat(result).isNull();
    }

    @Test
    void cannot_parse_without_RequestMapping() throws NoSuchMethodException {
        var method = TestPathVariableClass.class.getDeclaredMethod("cannot_parse_without_RequestMapping", String.class);
        var parameter = method.getParameters()[0];

        var result = parser.parse(method, parameter, null);

        assertThat(result).isNull();
    }

    @Test
    void can_parse_by_matched() throws NoSuchMethodException {
        var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/1/2");
        var method = TestPathVariableClass.class.getDeclaredMethod("can_parse_by_matched", long.class, int.class);

        var parameter1 = method.getParameters()[0];
        var parameter2 = method.getParameters()[1];
        assertThat(parser.parse(method, parameter1, request)).isEqualTo("1");
        assertThat(parser.parse(method, parameter2, request)).isEqualTo("2");
    }
}

class TestPathVariableClass {

    public void cannot_parse_without_pathVariableParameter(String id) {

    }

    public void cannot_parse_without_RequestMapping(@PathVariable String id) {

    }

    @RequestMapping(value = "/{a}/{b}")
    public void can_parse_by_matched(@PathVariable long a, @PathVariable int b) {

    }
}