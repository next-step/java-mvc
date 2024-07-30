package camp.nextstep.controller;

import camp.nextstep.support.HttpUtils;
import camp.nextstep.support.TomcatServerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@TomcatServerTest
class UserControllerTest {

    @DisplayName("요청이 오면 account 파라미터를 이용해 회원을 조회 후 조회 된 회원을 반환 한다")
    @Test
    public void userApi() throws Exception {
        final Map<String, Object> params = Map.of("account", "gugu");

        final HttpResponse<String> response = HttpUtils.get("/api/user", params);

        assertThat(response.body()).contains("account", "gugu");
    }

    @DisplayName("파라미터로 받은 account 로 user 를 조회할 수 없으면 에러 메시지를 반환 한다")
    @Test
    public void userApiNotFound() throws Exception {
        final Map<String, Object> params = Map.of("account", "djawnstj");

        final HttpResponse<String> response = HttpUtils.get("/api/user", params);

        assertThat(response.body()).contains("message", "user not found");
    }

}
