package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import org.junit.jupiter.api.Test;

import static com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter.SupportParameterType.isSupport;
import static org.assertj.core.api.Assertions.assertThat;

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
}
