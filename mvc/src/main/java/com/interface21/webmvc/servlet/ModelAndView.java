package com.interface21.webmvc.servlet;

import jakarta.annotation.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private final String view;
    private final Map<String, Object> model;

    public ModelAndView(@Nullable final String view) {
        this.view = view;
        this.model = new HashMap<>();
    }

    public ModelAndView addObject(final String attributeName, final Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public Object getObject(final String attributeName) {
        return model.get(attributeName);
    }

    public String getView() {
        return view;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }
}
