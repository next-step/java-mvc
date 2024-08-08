package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.tobe.meta.ControllerScanner;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final ControllerScanner controllerScanner;
    private final Object[] basePackages;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackages = basePackage;
        this.controllerScanner = new ControllerScanner();
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        controllerScanner.initialize(this.basePackages);


    }

    @Override
    public Object getHandler(final HttpServletRequest request) {
        HandlerKey key = HandlerKey.of(request.getRequestURI(),
            RequestMethod.from(request.getMethod()));

        return controllerScanner.get(key);
    }
}
