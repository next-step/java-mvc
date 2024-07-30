package com.interface21.webmvc.servlet.mvc.tobe.config;

import com.interface21.webmvc.servlet.mvc.asis.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandlerScanConfig {
    private ValueConfig valueConfig;
    private List<String> basePackages;

    public HandlerScanConfig(ValueConfig valueConfig) {
        this.valueConfig = valueConfig;
    }

    public List<String> getBasePackages() {
        if (basePackages == null) {
            basePackages = Arrays.asList((String[]) valueConfig.getValueMap().get("basePackages"));
        }
        return basePackages;
    }
}
