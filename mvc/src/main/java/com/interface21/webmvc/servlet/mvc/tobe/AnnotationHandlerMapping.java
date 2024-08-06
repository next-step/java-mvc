package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.tobe.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final HandlerExecutionMeta handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.handlerExecutions = new HandlerExecutionMeta(basePackage);
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
    }

    public Object getHandler(final HttpServletRequest request) {
        HandlerKey key = HandlerKey.of(request.getRequestURI(),
            RequestMethod.from(request.getMethod()));

        return handlerExecutions.get(key)
            .orElseThrow(NotFoundException::new);
    }
}
