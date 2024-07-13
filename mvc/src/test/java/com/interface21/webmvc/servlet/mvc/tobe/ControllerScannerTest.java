package com.interface21.webmvc.servlet.mvc.tobe;

import constructor.success.TestSuccessController;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ControllerScannerTest {

    @Test
    void Controller어노테이션이_걸린_클래스의_인스턴스를_생성하여_반환한다() {
        Map<Class<?>, Object> actual = new ControllerScanner("constructor.success").getControllers();
        assertAll(
                () -> assertThat(actual).containsKey(TestSuccessController.class),
                () -> assertThat(actual.get(TestSuccessController.class)).isInstanceOf(TestSuccessController.class)
        );
    }

    @Test
    void Controller_어노테이션이_인터페이스에_붙는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new ControllerScanner("constructor.instantiation").getControllers())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("클래스가 아닌 추상클래스, 인터페이스는 생성자를 만들 수 없습니다.");
    }

    @Test
    void Controller가_생성자가_접근불가능한_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new ControllerScanner("constructor.access").getControllers())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("클래스의 생성자에 접근할 수 없습니다.");
    }

    @Test
    void Controller생성자에서_예외가_발생하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new ControllerScanner("constructor.invocation").getControllers())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("생성자에서 예외가 발생했습니다.");
    }

    @Test
    void Controller가_지원생성자가_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new ControllerScanner("constructor.nosuch").getControllers())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("지원하는 생성자가 존재하지 않습니다.");
    }
}
