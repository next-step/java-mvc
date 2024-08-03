package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewResolvers {

    private final List<ViewResolver> viewResolvers = new ArrayList<>();
    private final String defaultSuffix;

    public ViewResolvers(String defaultSuffix) {
        this.defaultSuffix = defaultSuffix;
    }

    public void addViewResolver(ViewResolver viewResolver) {
        viewResolvers.add(viewResolver);
    }

    public void resolveAndRenderView(Object object, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (object instanceof ModelAndView modelAndView) {
            renderModelAndView(modelAndView, request, response);
            return;
        }

        if (object instanceof String viewName) {
            renderViewByName(viewName, request, response);
            return;
        }

        throw new IllegalArgumentException("지원하지 않는 타입입니다: " + object.getClass().getName());
    }

    private void addSuffixIfNotPresent(StringBuilder view) {
        if (!view.toString().endsWith(defaultSuffix)) {
            view.append(defaultSuffix);
        }
    }

    private void renderModelAndView(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) throws Exception {
        modelAndView.getView().render(modelAndView.getModel(), request, response);
    }

    private void renderViewByName(String viewName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuilder view = new StringBuilder(viewName);
        addSuffixIfNotPresent(view);
        viewResolvers.stream()
            .filter(viewResolver -> viewResolver.supports(view.toString()))
            .findFirst()
            .map(viewResolver -> viewResolver.resolveViewName(view.toString()))
            .orElseThrow(() -> new IllegalArgumentException(viewName + "에 해당하는 ViewResolver가 없습니다."))
            .render(Collections.emptyMap(), request, response);
    }
}
