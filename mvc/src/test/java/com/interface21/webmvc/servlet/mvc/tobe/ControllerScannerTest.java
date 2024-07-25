package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ControllerScannerTest {

    private Object[] basePackages = new Object[] {"com.interface21.webmvc.servlet.mvc.tobe.fixtures"};

    @DisplayName("@Controller가 명시된 클래스를 스캔한다")
    @Test
    public void scanControllerAnnotatedClassTest() {

        var classes = ControllerScanner.scanControllers(basePackages);
        assertThat(classes).hasSize(1);
    }

    @DisplayName("스캔한 클래스를 인스턴스화한다")
    @Test
    public void createInstance() {
        var classes = ControllerScanner.scanControllers(basePackages);
        var classObjectMap = ControllerScanner.newInstances(classes);

        assertThat(classObjectMap).hasSize(1);
    }
}
