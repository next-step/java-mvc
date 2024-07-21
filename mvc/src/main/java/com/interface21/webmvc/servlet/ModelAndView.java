package com.interface21.webmvc.servlet;

import com.interface21.webmvc.servlet.view.JsonView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModelAndView {

    private final View view;
    private final Map<String, Object> model;

    public ModelAndView(final View view) {
        this.view = view;
        this.model = new HashMap<>();
    }

    public static ModelAndView ofJspView(final String path) {
        return new ModelAndView(new JspView(path));
    }

    public static ModelAndView ofJsonView() {
        return new ModelAndView(new JsonView());
    }

    public ModelAndView addObject(final String attributeName, final Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public Object getObject(final String attributeName) {
        return model.get(attributeName);
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public View getView() {
        return view;
    }

    public void renderView(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        view.render(model, request, response);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ModelAndView that = (ModelAndView) o;
        return Objects.equals(view, that.view) && Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(view, model);
    }
}
