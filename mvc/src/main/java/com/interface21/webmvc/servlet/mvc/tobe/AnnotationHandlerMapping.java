package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        final ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        final List<Object> controllerBeans = controllerScanner.createControllerBeans();

        mappingRegistry.registerControllers(controllerBeans);
    }

    public Object getHandler(final HttpServletRequest request) {
        return this.mappingRegistry.getMethod(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
    }
}
