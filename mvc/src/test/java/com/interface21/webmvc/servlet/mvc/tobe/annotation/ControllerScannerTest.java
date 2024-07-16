package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import constructor.success.TestSuccessController;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {

    @Test
    void Controller어노테이션이_걸린_클래스의_인스턴스를_생성하여_반환한다() {
        List<ControllerInstance> actual = new ControllerScanner("constructor.success").getControllers();
        assertThat(actual).containsExactly(new ControllerInstance(TestSuccessController.class));
    }
}
