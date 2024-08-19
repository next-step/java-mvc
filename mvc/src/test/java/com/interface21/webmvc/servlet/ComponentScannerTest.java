package com.interface21.webmvc.servlet;

import com.interface21.context.stereotype.Controller;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ComponentScannerTest {

    @Test
    void test() {
        var components = ComponentScanner.scan(Controller.class, "samples");
        Assertions.assertThat(components).hasSize(2);
    }

}