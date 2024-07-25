package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.interface21.web.bind.annotation.RequestMapping;

class MethodScannerTest {

    @DisplayName("클래스에서 어노테이션이 명시된 메서드를 모두 스캔한다")
    @Test
    public void getAnnotationInstanceTest() {

        var classes =
                ControllerScanner.scanControllers(
                        new Object[] {"com.interface21.webmvc.servlet.mvc.tobe.fixtures"});
        var methods =
                classes.stream()
                        .map(
                                clazz ->
                                        MethodScanner.scanMethodsWithAnnotation(
                                                clazz, RequestMapping.class))
                        .toList();

        assertThat(methods).hasSize(1);
    }
}
