package com.interface21.webmvc.servlet.view;

import com.interface21.web.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JsonViewTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);
    }

    @AfterEach
    void tearDown() {
        printWriter.close();
    }

    @Test
    @DisplayName("model 에 데이터가 1개 인데 Primitive 타입이면 응답값을 그대로 반환한다")
    void primitiveValueTest() throws Exception {
        new JsonView().render(Map.of("testKey", 1), request, response);

        verify(response).setContentType(MediaType.TEXT_PLAIN);
        assertThat(stringWriter.toString()).isEqualTo("1");
    }

    @Test
    @DisplayName("model 에 데이터가 1개 인데 String 이면 응답값을 그대로 반환한다")
    void stringValueTest() throws Exception {
        new JsonView().render(Map.of("testKey", "testValue"), request, response);

        verify(response).setContentType(MediaType.TEXT_PLAIN);
        assertThat(stringWriter.toString()).isEqualTo("testValue");
    }

    @Test
    @DisplayName("model 에 데이터가 1개 인데 String 이 아닌 객체이면 응답값을 json 형태로 내려줄 수 있다")
    void jsonValueTest() throws Exception {
        new JsonView().render(Map.of("testKey", Map.of("innerKey", "innerValue")), request, response);

        verify(response).setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(stringWriter.toString()).isEqualTo("{\"testKey\":{\"innerKey\":\"innerValue\"}}");
    }

    @Test
    @DisplayName("model 에 데이터가 2개 이상이면 응답값을 json 형태로 내려줄 수 있다")
    void referenceTypeTest() throws Exception {
        new JsonView().render(Map.of("firstKey", "firstValue", "secondKey", "secondValue"), request, response);

        verify(response).setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        final String jsonResponse = stringWriter.toString();
        assertSoftly(softly -> {
            softly.assertThat(jsonResponse).startsWith("{");
            softly.assertThat(jsonResponse).contains("\"firstKey\":\"firstValue\"");
            softly.assertThat(jsonResponse).contains("\"secondKey\":\"secondValue\"");
            softly.assertThat(jsonResponse).endsWith("}");
        });
    }
}
