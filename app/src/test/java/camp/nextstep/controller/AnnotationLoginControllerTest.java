package camp.nextstep.controller;

import camp.nextstep.support.HttpUtils;
import camp.nextstep.support.TomcatServerTest;
import org.junit.jupiter.api.*;

import java.net.http.HttpResponse;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TomcatServerTest
class AnnotationLoginControllerTest {
    @DisplayName("로그인 뷰 요청을 하면 login.jsp 를 반환 한다")
    @Test
    public void loginView() throws Exception {
        final String path = "/login/view";
        final HttpResponse<String> response = HttpUtils.get(path);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(response.body()).contains("login")
        );
    }

    @DisplayName("없는 회원으로 로그인 요청을 하면 로그인 처리 후 401.jsp 로 리다이렉트 한다")
    @Test
    public void notMatchPassword() throws Exception {
        final String path = "/login";
        final HttpResponse<String> response = HttpUtils.post(path, Map.of("account", "djawnstj", "password", "password"));

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(302),
                () -> assertThat(response.headers().firstValue("location").orElse(null)).isEqualTo("/401.jsp")
        );
    }

    @DisplayName("정상 로그인 요청을 하면 로그인 처리 후 index.jsp 로 리다이렉트 한다")
    @Test
    public void loginSuccess() throws Exception {
        final String path = "/login";
        final HttpResponse<String> response = HttpUtils.post(path, Map.of("account", "gugu", "password", "password"));

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(302),
                () -> assertThat(response.headers().firstValue("location").orElse(null)).isEqualTo("/index.jsp")
        );
    }

    @DisplayName("로그아웃 요청을 하면 / 경로로 리다이렉트 한다")
    @Test
    public void logout() throws Exception {
        final String path = "/logout";
        final HttpResponse<String> response = HttpUtils.get(path);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(302),
                () -> assertThat(response.headers().firstValue("location").orElse(null)).isEqualTo("/")
        );
    }
}
