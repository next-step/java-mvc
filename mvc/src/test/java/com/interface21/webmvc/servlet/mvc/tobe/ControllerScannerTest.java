package com.interface21.webmvc.servlet.mvc.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestController;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {

    @DisplayName("@Controller 가 붙은 클래스를 찾아 인스턴스를 생성 한다")
    @Test
    public void createControllerBeans() throws Exception {
        // given
        final ControllerScanner scanner = new ControllerScanner(new String[]{"samples"});

        // when
        final List<Object> actual = scanner.getControllerBeans();

        // then
        assertThat(actual)
                .hasSize(1)
                .hasExactlyElementsOfTypes(TestController.class);
    }

}
