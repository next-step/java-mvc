package com.interface21.webmvc.servlet.mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappingRegistry handlerMappingRegistry;
    private final HandlerAdapter handlerAdapter;

    public DispatcherServlet(Object... basePackage) {
        handlerMappingRegistry = new HandlerMappingRegistry(new AnnotationHandlerMapping(basePackage));
        handlerAdapter = new RequestHandlerAdapter();
    }

    @Override
    public void init() {
        handlerMappingRegistry.initialize();
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            final var handler = handlerMappingRegistry.getHandlerMapping(request);
            handlerAdapter.handle(request, response, handler);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }
}
