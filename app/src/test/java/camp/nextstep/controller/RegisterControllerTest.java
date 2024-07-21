package camp.nextstep.controller;

import camp.nextstep.controller.support.RequestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class RegisterControllerTest {

    @Test
    void register() {
        var response = RequestUtils.send("/register?account=woo-yu&password=test&email=hi");

        Assertions.assertThat(response.statusCode()).isEqualTo(302);
        Assertions.assertThat(response.headers().firstValue("Location").orElse(null)).isEqualTo("/index.jsp");
    }

}