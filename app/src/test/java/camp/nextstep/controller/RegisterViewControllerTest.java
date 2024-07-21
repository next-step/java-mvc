package camp.nextstep.controller;

import camp.nextstep.controller.support.RequestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RegisterViewControllerTest {

    @Test
    void registerView() {
        var response = RequestUtils.send("/register/view");

        Assertions.assertThat(response.body()).contains("<title>회원가입</title>");

    }

}