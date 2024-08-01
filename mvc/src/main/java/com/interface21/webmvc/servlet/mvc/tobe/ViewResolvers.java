package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import java.util.ArrayList;
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

    public View handleResolver(Object object) {
        if (object instanceof ModelAndView modelAndView) {
            return modelAndView.getView();
        }

        if (object instanceof String viewName) {
            StringBuilder view = new StringBuilder(viewName);
            addSuffixIfNotPresent(view);
            return viewResolvers.stream()
                .filter(viewResolver -> viewResolver.supports(view.toString()))
                .map(viewResolver -> viewResolver.resolveViewName(view.toString()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(viewName + "에 해당하는 ViewResolver가 없습니다."));
        }

        throw new IllegalArgumentException("지원하지 않는 타입입니다." + object.getClass().getName());
    }

    private void addSuffixIfNotPresent(StringBuilder view) {
        if (!view.toString().endsWith(defaultSuffix)) {
            view.append(defaultSuffix);
        }
    }
}
