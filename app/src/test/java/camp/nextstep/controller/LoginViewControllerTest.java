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

}