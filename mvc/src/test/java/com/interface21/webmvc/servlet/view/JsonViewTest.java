package com.interface21.webmvc.servlet.view;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class JsonViewTest {

    @DisplayName("JsonView render 테스트 : 1개의 모델을 받아서 json으로 변환한다.")
    @Test
    void renderTest() throws Exception {
        // given
        final JsonView jsonView = new JsonView();
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Map<String, ?> model = Map.of("key", "value");
        PrintWriter writer = mock(PrintWriter.class);
        given(response.getWriter()).willReturn(writer);

        // when
        jsonView.render(model, null, response);

        // then
        verify(writer).write("value");
    }

    @DisplayName("JsonView render 테스트 : 여러 개의 모델을 받아서 json으로 변환한다.")
    @Test
    void multiRenderTest() throws Exception {
        // given
        final JsonView jsonView = new JsonView();
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Map<String, ?> model1 = Map.of("key1", "value1");
        final Map<String, ?> model2 = Map.of("key2", "value2");
        final Map<String, Map<String, ?>> model = new LinkedHashMap<>();
        model.put("model1", model1);
        model.put("model2", model2);
        PrintWriter writer = mock(PrintWriter.class);
        given(response.getWriter()).willReturn(writer);

        // when
        jsonView.render(model, null, response);

        // then
        verify(writer).write("{\"model1\":{\"key1\":\"value1\"},\"model2\":{\"key2\":\"value2\"}}");
    }
}
