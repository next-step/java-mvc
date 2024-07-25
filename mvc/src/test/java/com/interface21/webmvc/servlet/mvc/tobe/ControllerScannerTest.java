package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.interface21.webmvc.servlet.mvc.tobe.fixtures.MockController;

class ControllerScannerTest {

    private Object[] basePackages =
            new Object[] {"com.interface21.webmvc.servlet.mvc.tobe.fixtures"};

    @DisplayName("@Controller가 명시된 MockController 클래스를 스캔한다")
    @Test
    public void scanControllerAnnotatedClassTest() {

        var classes = ControllerScanner.scanControllers(basePackages);
        for (Class<?> aClass : classes) {
            assertThatNoException().isThrownBy(() -> aClass.asSubclass(MockController.class));
        }
    }

    @DisplayName("스캔한 클래스가 정상적으로 생성되었다면 타입은 Object 이다")
    @Test
    public void createInstance() {
        var classes = ControllerScanner.scanControllers(basePackages);
        var instances = ControllerScanner.newInstances(classes);

        assertThat(instances).hasSize(1);
        assertThat(instances).isInstanceOf(Object.class);
    }
}
