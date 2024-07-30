package camp.nextstep.controller;


import camp.nextstep.controller.support.RequestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserControllerTest {

    /**
     * objectMapper 기본 설정으로 {@link com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std.DEFAULT} 를 사용하고 있으며,
     * User는 field가 전부 private하며, setter는 없고, public한 getter가 account만 존재하기 때문에 account만 읽어온다.
     * 필드 추가가 필요하면 visibility 변경, jackson annotation 활용, getter/setter 추가 등의 다양한 방식을 활용할 수 있다.
     */

    @Test
    void user() {
        var response = RequestUtils.send("/api/user?account=gugu&password=password");

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.body()).isEqualTo("{\"user\":{\"account\":\"gugu\"}}");
    }

}