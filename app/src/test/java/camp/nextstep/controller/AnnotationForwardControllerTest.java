package camp.nextstep.controller;

import camp.nextstep.support.HttpUtils;
import camp.nextstep.support.TomcatServerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@TomcatServerTest
class AnnotationForwardControllerTest {

  @DisplayName("루트 경로로 요청을 하면 index.jsp 를 반환 한다")
  @Test
  public void rootForward() throws Exception {
      final String path = "/";
      final HttpResponse<String> response = HttpUtils.get(path);

      assertThat(response.body()).contains("index");
  }
}
