package com.interface21.web.bind.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class RequestMethodTest {

    @DisplayName("from 메서드 테스트")
    @EnumSource(RequestMethod.class)
    @ParameterizedTest
    public void fromTest(RequestMethod method) {
        assertEquals(method, RequestMethod.from(method.name()));
    }

    @DisplayName("RequestMethod에 정의되지 않은 문자열로 from 메서드 호출하면 예외 발생한다")
    @Test
    public void fromFailTest() {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> RequestMethod.from("TEST"));
    }
}
