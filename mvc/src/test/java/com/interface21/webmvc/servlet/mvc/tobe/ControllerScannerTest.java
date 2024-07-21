package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.success.TestController;
import samples.success.WithClassRequestMappingAnnotation;

class ControllerScannerTest {
    @DisplayName("지정된 basePackage를 스캔하고 @Controller 애노테이션이 붙은 클래스들을 인스턴스화 한다.")
    @Test
    void test() {
        String basePackage = "samples.success";
        ControllerScanner controllerScanner = ControllerScanner.from(basePackage);

        Map<Class<?>, Object> scanResult = controllerScanner.scan();

        Class<TestController> class1 = TestController.class;
        Class<WithClassRequestMappingAnnotation> class2 = WithClassRequestMappingAnnotation.class;
        assertAll(
                () -> assertThat(scanResult).containsKeys(class1, class2),
                () -> assertThat(scanResult.get(class1)).isInstanceOf(class1),
                () -> assertThat(scanResult.get(class2)).isInstanceOf(class2)
        );
    }

    @DisplayName("@Controller 애노테이션이 붙은 클래스에 기본 생성자가 없을 경우 예외를 발생시킨다")
    @Test
    void test2() {
        String basePackage = "samples.fail";
        ControllerScanner controllerScanner = ControllerScanner.from(basePackage);

        assertThatThrownBy(controllerScanner::scan)
                .isInstanceOf(ControllerDefaultConstructorNotFoundException.class);
    }
}