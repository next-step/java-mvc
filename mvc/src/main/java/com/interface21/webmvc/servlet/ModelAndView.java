package com.interface21.webmvc.servlet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private final Object view;
    private final Map<String, Object> model;

    public ModelAndView(final Object view) {
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

    public View getView() {
        return view instanceof View ? (View) view : null;
    }

    public String getViewName() {
        return view instanceof String ? (String) view : null;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }
}
