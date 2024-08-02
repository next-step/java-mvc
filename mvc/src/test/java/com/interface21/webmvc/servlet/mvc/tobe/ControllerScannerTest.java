package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ControllerScannerTest {

  @Test
  @DisplayName("지정한 패키지 내의 @Controller 어노테이션이 붙은 클래스를 스캔하여 리스트를 반환한다")
  void scanControllersIncludeBasePackages() {
    ControllerScanner controllerScanner = ControllerScanner.getInstance();
    Controllers controllers = controllerScanner.scan("samples");

    assertThat(controllers.values()).hasSize(1);
  }
}