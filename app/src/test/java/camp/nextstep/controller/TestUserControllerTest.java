package camp.nextstep.controller;

import camp.nextstep.support.HttpUtils;
import camp.nextstep.support.TomcatServerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@TomcatServerTest
class TestUserControllerTest {
    
    @DisplayName("String 타입의 파라미터를 매핑해서 받는다")
    @Test
    public void create_string() throws Exception {
        final HttpResponse<String> actual = HttpUtils.post("/resolvers/string", Map.of("userId", "gugu", "password", "password"));

        assertThat(actual.body()).contains("\"userId\":\"gugu\"", "\"password\":\"password\"");
    }
}
