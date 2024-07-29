package camp.nextstep.controller;

import camp.nextstep.support.HttpUtils;
import camp.nextstep.support.TomcatServerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TomcatServerTest
class AnnotationRegisterControllerTest {
    @DisplayName("post 메서드로 /register 요청을 하면 회원가입 후 index.jsp 로 리다이렉트 한다")
    @Test
    public void postRegister() throws Exception {
        final String path = "/register";
        final HttpResponse<String> response = HttpUtils.post(path, Map.of("account", "djawnstj", "password", "password", "email", "djawnstj@http.com"));

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(302),
                () -> assertThat(response.headers().firstValue("location").orElse(null)).isEqualTo("/index.jsp")
        );
    }

    @DisplayName("register 뷰 요청을 하면 register.jsp 로 리다이렉트 한다")
    @Test
    public void registerView() throws Exception {
        final String path = "/register/view";
        final HttpResponse<String> response = HttpUtils.get(path);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(response.body()).contains("register")
        );
    }
}
