package com.interface21.webmvc.servlet.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class JsonViewTest {

    final JsonView jsonView = new JsonView();

    @DisplayName("모델을 json 으로 변환 하여 응답 한다")
    @Test
    public void jsonRender() throws Exception {
        final Map<String, ?> model = Map.of("name", "홍길동", "age", 27);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final PrintWriter printWriter = new PrintWriter(out);

        Mockito.when(response.getWriter()).thenReturn(printWriter);

        jsonView.render(model, request, response);

        final String actual = out.toString();
        printWriter.close();

        assertThat(actual).contains("\"age\":27", "\"name\":\"홍길동\"");
    }

    @DisplayName("빈 모델을 json 으로 반환 하면 빈 json 이 반환 된다")
    @Test
    public void emptyJsonRender() throws Exception {
        final Map<String, ?> model = Map.of();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final PrintWriter printWriter = new PrintWriter(out);

        Mockito.when(response.getWriter()).thenReturn(printWriter);

        jsonView.render(model, request, response);

        final String actual = out.toString();
        printWriter.close();

        assertThat(actual).contains("{}");
    }
}
