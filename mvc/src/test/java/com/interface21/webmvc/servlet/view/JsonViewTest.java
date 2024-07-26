package com.interface21.webmvc.servlet.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.SerializationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import samples.TestUser;

class JsonViewTest {

    @Test
    @DisplayName("JsonView는 model의 크기가 1일 경우 toString()을 직렬화한다")
    public void serializeNotJsonTest() {
        Map<String, String> model = new TreeMap<>();
        model.put("id", "gugu");

        assertJsonViewSerializeReuslt(model, "{id=gugu}");
    }

    @Test
    @DisplayName("JsonView는 model의 크기가 1보다 클 경우 JSON 형태로 직렬화한다")
    public void serializeJsonTest() {
        Map<String, String> model = new TreeMap<>();
        model.put("property1", "gugu");
        model.put("property2", "password");

        assertJsonViewSerializeReuslt(model, "{\"property1\":\"gugu\",\"property2\":\"password\"}");
    }

    @Test
    @DisplayName("JsonView는 POJO를 JSON으로 직렬화한다")
    public void serializePojoToJsonTest() {

        Map<String, TestUser> model = new TreeMap<>();
        final var aUser = new TestUser("userA", "password", 10);
        final var bUser = new TestUser("userB", "password", 10);
        model.put("userA", aUser);
        model.put("userB", bUser);

        assertJsonViewSerializeReuslt(
                model,
                "{\"userA\":{\"userId\":\"userA\",\"password\":\"password\",\"age\":10},\"userB\":{\"userId\":\"userB\",\"password\":\"password\",\"age\":10}}");
    }

    @Test
    @DisplayName("JsonView는 직렬화에 실패하면 SerializationException을 던진다")
    public void serializeFailTest() throws IOException {

        final var jsonView = new JsonView();
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        final var model = new TreeMap<String, String>();
        model.put("id", "gugu");

        // when
        when(response.getWriter()).thenThrow(new IOException());

        // then
        assertThatThrownBy(() -> jsonView.render(model, request, response))
                .isInstanceOf(SerializationException.class);
    }

    private void assertJsonViewSerializeReuslt(Map<String, ?> model, String expectResult) {

        final var jsonView = new JsonView();
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        final var sw = new StringWriter();

        try {
            // when
            when(response.getWriter()).thenReturn(mockPrintWriter(sw));

            // then
            jsonView.render(model, request, response);
            assertThat(mockBufferedReader(sw).lines()).contains(expectResult);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PrintWriter mockPrintWriter(final StringWriter writer) {
        return new PrintWriter(writer);
    }

    private BufferedReader mockBufferedReader(final StringWriter writer) {
        return new BufferedReader(new StringReader(writer.toString()));
    }
}
