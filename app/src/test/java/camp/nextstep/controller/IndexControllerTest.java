package camp.nextstep.controller;

import camp.nextstep.controller.support.RequestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class IndexControllerTest {

    @Test
    void test() {
        var response = RequestUtils.send("/");

        Assertions.assertThat(response.body()).contains("<title>대시보드</title>");
    }
}