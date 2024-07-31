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

    @DisplayName("long, int 타입의 파라미터를 매핑해서 받는다")
    @Test
    public void create_int_long() throws Exception {
        final HttpResponse<String> actual = HttpUtils.post("/resolvers/primitive/number", Map.of("id", 1000, "age", 28));

        assertThat(actual.body()).contains("\"id\":1000", "\"age\":28");
    }

    @DisplayName("객체 파라미터를 매핑해서 받는다")
    @Test
    public void create_javabean() throws Exception {
        final HttpResponse<String> actual = HttpUtils.post("/resolvers/object", Map.of("userId", "gugu", "password", "password", "age", 28));

        assertThat(actual.body()).contains("\"userId\":\"gugu\"", "\"password\":\"password\"", "\"age\":28");
    }

    @DisplayName("path 파라미터를 매핑해서 받는다")
    @Test
    public void show_pathvariable() throws Exception {
        final HttpResponse<String> actual = HttpUtils.get("/resolvers/path/1");

        assertThat(actual.body()).contains("\"id\":1");
    }
}
