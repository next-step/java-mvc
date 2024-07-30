package camp.nextstep.controller;


import camp.nextstep.controller.support.RequestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserControllerTest {

    @Test
    void user() {
        var response = RequestUtils.send("/api/user?account=gugu&password=password");

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.body()).isEqualTo("{\"user\":{\"account\":\"gugu\"}}");
    }

}