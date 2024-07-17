package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ControllerScannerTest {
    
    @DisplayName("Controller 어노테이션이 붙은 클래스를 찾아 인스턴스화한다.")
    @Test
    void testGetControllers() {
        // given
        final ControllerScanner controllerScanner = new ControllerScanner("com.interface21.webmvc.servlet.mvc.tobe");

        // when
        final Map<Class<?>, Object> controllers = controllerScanner.getControllers();

        // then
        assertAll(
                () -> assertThat(controllers.keySet().size()).isEqualTo(1),
                () -> assertThat(controllers.get(TestController.class)).isNotNull()
        );
    }


    @Controller
    static class TestController {

    }
}
