package com.interface21.webmvc.servlet.mvc.tobe.meta;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerKey;
import com.interface21.webmvc.servlet.mvc.tobe.parameter.config.ResolverRegistryConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ControllerScannerTest {

    @Test
    @DisplayName("ControllerScanner가 @Controller 클래스들을 가져와서 Mapping을 생성합니다.")
    void get() {

        ControllerScanner controllerScanner = new ControllerScanner(
            ResolverRegistryConfig.getResolverRegistry());
        controllerScanner.initialize("samples");

        assertThat(controllerScanner.get(new HandlerKey("/no-method", RequestMethod.GET)))
            .isInstanceOf(HandlerExecution.class);
    }

}
