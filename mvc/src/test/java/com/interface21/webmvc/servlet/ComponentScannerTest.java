package com.interface21.webmvc.servlet;

import com.interface21.context.stereotype.Controller;
import com.interface21.webmvc.servlet.ComponentScanner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import samples.TestController;

class ComponentScannerTest {

    @Test
    void test() {
        var components = ComponentScanner.scan(Controller.class, "samples");
        Assertions.assertThat(components).hasSize(1);
        Assertions.assertThat(components.get(0).methods).hasSize(2);
    }

}