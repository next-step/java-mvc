package com.interface21.webmvc.servlet.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JspViewTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @DisplayName("viewName 이 없을 경우 예외를 던진다")
    void createFailTest(final String viewName) {
        assertThatThrownBy(() -> new JspView(viewName));
    }

}
