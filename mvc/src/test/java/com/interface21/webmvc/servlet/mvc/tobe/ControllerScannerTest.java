package com.interface21.webmvc.servlet.mvc.tobe;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import samples.TestController;

class ControllerScannerTest {

    @Test
    void test() {
        var controllers = new ControllerScanner("samples").getControllers();
        Assertions.assertThat(controllers.get(TestController.class)).isNotNull();
    }

}