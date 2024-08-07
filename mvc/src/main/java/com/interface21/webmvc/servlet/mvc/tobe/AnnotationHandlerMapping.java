package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.tobe.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final HandlerExecutionMeta handlerExecutionMeta;
    private final Object[] basePackages;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackages = basePackage;
        this.handlerExecutionMeta = new HandlerExecutionMeta();
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        handlerExecutionMeta.initialize(this.basePackages);
    }

    public Object getHandler(final HttpServletRequest request) {
        HandlerKey key = HandlerKey.of(request.getRequestURI(),
            RequestMethod.from(request.getMethod()));

        return handlerExecutionMeta.get(key)
            .orElseThrow(NotFoundException::new);
    }
}
