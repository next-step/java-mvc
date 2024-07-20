package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import org.junit.jupiter.api.Test;

import static com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter.SupportParameterType.isSupport;
import static com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter.SupportParameterType.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SupportParameterTypeTest {

    @Test
    void 지원가능한_타입인지_확인한다() {
        boolean actual = isSupport(Integer.class);
        assertThat(actual).isTrue();
    }

    @Test
    void 지원불가능한_타입인지_확인한다() {
        boolean actual = isSupport(SupportParameterType.class);
        assertThat(actual).isFalse();
    }

    @Test
    void 지원하지_않는_파라미터를_파싱하려는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> SupportParameterType.parse(double.class, 1.1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("지원가능한 타입이 없습니다.");
    }

    @Test
    void 지원하는_파라미터로_파싱한다() {
        Object actual = parse(int.class, "123");
        assertThat(actual).isEqualTo(123)
                .isInstanceOf(Integer.class);
    }
}
