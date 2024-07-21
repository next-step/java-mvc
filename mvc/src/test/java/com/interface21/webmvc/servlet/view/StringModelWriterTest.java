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

class StringModelWriterTest {

    @Test
    @DisplayName("support 메서드는 모델의 크기가 1이고 primitive 일 경우 true 를 반환한다.")
    void supportSinglePrimitiveTest() {
        final StringModelWriter writer = new StringModelWriter();

        final boolean result = writer.support(Map.of("key", 1));

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("support 메서드는 모델의 크기가 1이고 String 일 경우 true 를 반환한다.")
    void supportSingleStringTest() {
        final StringModelWriter writer = new StringModelWriter();

        final boolean result = writer.support(Map.of("key", "value"));

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("support 메서드는 모델의 크기가 1이고 primitive 또는 String이 아닌 경우 false를 반환한다.")
    void supportSingleNonPrimitiveOrStringTest() {
        final StringModelWriter writer = new StringModelWriter();

        final boolean result = writer.support(Map.of("key", new Object()));

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("write 메서드는 모델의 첫 번째 값을 plain text로 HttpServletResponse에 출력한다.")
    void writeTest() throws IOException {
        final StringModelWriter writer = new StringModelWriter();
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);
        writer.write(Map.of("key", "testValue"), response);

        verify(response).setContentType(MediaType.TEXT_PLAIN);
        assertThat(stringWriter.toString()).isEqualTo("testValue");
    }
}
