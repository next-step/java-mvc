package com.interface21.webmvc.servlet.mvc;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.ModelAndView;
import java.lang.reflect.Method;

public class HandlerExecution {

    private final AnnotationControllerClass controller;
    private final Method method;

    public HandlerExecution(AnnotationControllerClass controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Method method() {
        return this.method;
    }

    public String urlPattern() {
        return this.method.getAnnotation(RequestMapping.class).value();
    }

    public ModelAndView handle(final Object[] arguments) throws Exception {
        return (ModelAndView) method.invoke(controller.getNewInstance(), arguments);
    }
}
