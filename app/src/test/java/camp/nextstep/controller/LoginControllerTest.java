package camp.nextstep.controller;


import camp.nextstep.controller.support.RequestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LoginControllerTest {

    @Test
    void no_user() {
        var response = RequestUtils.send("/login?account=no");

        Assertions.assertThat(response.statusCode()).isEqualTo(302);
        Assertions.assertThat(response.headers().firstValue("Location").orElse(null)).isEqualTo("/401.jsp");
    }

    @Test
    void user() {
        var response = RequestUtils.send("/login?account=gugu&password=password");

        Assertions.assertThat(response.statusCode()).isEqualTo(302);
        Assertions.assertThat(response.headers().firstValue("Location").orElse(null)).isEqualTo("/index.jsp");
        Assertions.assertThat(response.headers().firstValue("Set-Cookie").orElse(null)).isNotNull();
    }

}