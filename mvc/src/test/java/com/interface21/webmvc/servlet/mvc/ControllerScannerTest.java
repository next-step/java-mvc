package com.interface21.webmvc.servlet.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.webmvc.servlet.mvc.support.ControllerScanner;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestController;

class ControllerScannerTest {

    @Test
    @DisplayName("Controller 애노테이션이 붙은 클래스를 찾아서 인스턴스화한다.")
    void scan() {
        // given
        ControllerScanner controllerScanner = new ControllerScanner("samples");

        // when
        Map<Class<?>, Object> result = controllerScanner.scan();

        // then
        assertThat(result).containsKey(TestController.class);
    }
}
