package com.interface21.webmvc.servlet.mvc.tobe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ControllerScanner 클래스는")
class ControllerScannerTest {

    @DisplayName("scan 메서드를 실행하면 Controller 어노테이션이 붙은 클래스를 찾아 인스턴스를 생성한다.")
    @Test
    void scan() {
        // given
        final ControllerScanner controllerScanner = new ControllerScanner("samples");

        // when
        final var controllers = controllerScanner.scan();

        // then
        assertEquals(1, controllers.size());
    }
}
