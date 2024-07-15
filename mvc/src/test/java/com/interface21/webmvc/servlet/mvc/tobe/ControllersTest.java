package com.interface21.webmvc.servlet.mvc.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllersTest {

    @Test
    @DisplayName("특정 패키지 밑의 `@Controller` 를 가진 클래스들을 생성할 수 있다")
    void createTest() {
        final Controllers controllers = new Controllers("samples");

        assertThat(controllers).hasSize(1);
    }

}
