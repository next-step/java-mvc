package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.JsonViewResolver;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.JspViewResolver;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.RedirectViewResolver;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ViewResolvers {
    private final List<ViewResolver> viewResolvers;

    public ViewResolvers() {
        this.viewResolvers = new ArrayList<>();

    }

    public void initialize() {
        viewResolvers.add(new RedirectViewResolver());
        viewResolvers.add(new JspViewResolver());
        viewResolvers.add(new JsonViewResolver());
    }

    public ViewResolver findResolver(@Nullable ModelAndView modelAndView,
                                     @Nullable HandlerExecution handlerExecution) {
        if (modelAndView == null) {
            throw new IllegalStateException("템플릿을 찾을 수 없습니다 XXX");
        }

        return viewResolvers.stream()
                            .filter(it -> it.accept(modelAndView, handlerExecution))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("템플릿을 찾을 수 없습니다 XXX"));
    }
}
