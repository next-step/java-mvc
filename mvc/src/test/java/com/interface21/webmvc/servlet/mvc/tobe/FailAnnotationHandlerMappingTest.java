package com.interface21.webmvc.servlet.mvc.tobe;


import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.interface21.webmvc.servlet.mvc.tobe.exception.ControllerInitializationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FailAnnotationHandlerMappingTest {

    @Test
    @DisplayName("Controller가 생성자가 Private이면, Mapping Initialization가 실패합니다.")
    void get() {

        assertThatThrownBy(() -> new AnnotationHandlerMapping("noconstructor").initialize())
            .isInstanceOf(ControllerInitializationException.class);
    }
}
