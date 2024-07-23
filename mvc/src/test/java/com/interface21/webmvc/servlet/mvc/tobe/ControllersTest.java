package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.exception.ControllerInitializeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ControllersTest {

    @Test
    @DisplayName("특정 패키지 밑의 `@Controller` 를 가진 클래스들을 생성할 수 있다")
    void createTest() {
        final Controllers controllers = new Controllers("samples");

        assertThat(controllers).hasSize(2);
    }

    @Test
    @DisplayName("`@Controller` 클래스는 기본생성자가 필요하다")
    void defaultArgNotFoundTest() {
        assertThatThrownBy(() -> new Controllers("fake.fail.no_default_arg"))
                .isInstanceOf(ControllerInitializeException.class)
                .hasMessageStartingWith("No-arg constructor is not found from");
    }

    @Test
    @DisplayName("`@Controller` 클래스는 예외가 던져지면 전파된다")
    void instanceCreateFailTest() {
        assertThatThrownBy(() -> new Controllers("fake.fail.exception"))
                .isInstanceOf(ControllerInitializeException.class)
                .hasMessageStartingWith("Fail to create instance for ");
    }
}
