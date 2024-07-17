package com.interface21.webmvc.servlet.view;

import com.interface21.web.http.MediaType;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ObjectModelWriterTest {
    @Test
    @DisplayName("support 메서드는 모델이 비어 있으면 false 를 반환한다.")
    void supportEmptyModelTest() {
        final ObjectModelWriter writer = new ObjectModelWriter();

        final boolean result = writer.support(Map.of());

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("write 메서드는 모델을 JSON 형태로 출력한다.")
    void writeMethodTest() throws IOException {
        final ObjectModelWriter writer = new ObjectModelWriter();
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);
        writer.write(Map.of("key", Map.of("innerKey", "innerValue")), response);

        verify(response).setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(stringWriter.toString()).isEqualTo("{\"key\":{\"innerKey\":\"innerValue\"}}");
    }
}
