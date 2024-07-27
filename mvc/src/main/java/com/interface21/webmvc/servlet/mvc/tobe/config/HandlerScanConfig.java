package com.interface21.webmvc.servlet.mvc.tobe.config;

import java.util.List;

public class HandlerScanConfig {
    private List<String> basePackages;

    public List<String> getBasePackages() {
        if (basePackages == null) {
            basePackages =
                    List.of("com.interface21.webmvc.servlet.mvc.tobe.controller");
        }
        return basePackages;
    }
}
