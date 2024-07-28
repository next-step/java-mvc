package camp.nextstep.controller;

import camp.nextstep.controller.support.RequestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LoginViewControllerTest {

    @Test
    void loginView() {
        var response = RequestUtils.send("/login/view");

        Assertions.assertThat(response.body()).contains("<title>로그인</title>");
    }

    @Test
    void wrong_path() {
        var response = RequestUtils.send("/login/view/");

        Assertions.assertThat(response.statusCode()).isEqualTo(404);
    }

    @Test
    void wrong_method() {
        var response = RequestUtils.sendPost("/login/view");

        Assertions.assertThat(response.statusCode()).isEqualTo(405);
    }

}