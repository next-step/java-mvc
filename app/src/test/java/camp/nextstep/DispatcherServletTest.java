package camp.nextstep;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DispatcherServletTest {

    private DispatcherServlet sut;

    @BeforeEach
    void setUp() {
        sut = new DispatcherServlet();
    }

    @DisplayName("초기화 테스트")
    @Test
    public void test() throws Exception {

        Assertions.assertThatNoException()
                        .isThrownBy(() -> sut.init());
    }

}