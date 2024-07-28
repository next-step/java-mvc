package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.handleradapter.AnnotationHandlerAdapter;
import com.interface21.webmvc.servlet.mvc.handleradapter.HandlerAdapter;
import com.interface21.webmvc.servlet.mvc.handleradapter.HandlerAdapterRegistry;
import com.interface21.webmvc.servlet.mvc.handlermapping.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.handlermapping.HandlerMappingRegistry;
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
    private final HandlerAdapterRegistry handlerAdapterRegistry;

    public DispatcherServlet() {
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping();
        this.handlerMappingRegistry = new HandlerMappingRegistry(annotationHandlerMapping);

        AnnotationHandlerAdapter annotationHandlerAdapter = new AnnotationHandlerAdapter();
        this.handlerAdapterRegistry = new HandlerAdapterRegistry(annotationHandlerAdapter);
    }

    @Override
    public void init() {
        log.info("DispatcherServlet is initialized");
        this.handlerMappingRegistry.initialize();
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        log.debug("Method : {}, Request URI : {}", request.getMethod(), request.getRequestURI());

        try {
            final Object handlerMapping = handlerMappingRegistry.getHandler(request);
            final HandlerAdapter handlerAdapter = handlerAdapterRegistry.getHandlerAdapter(handlerMapping);
            ModelAndView modelAndView = handlerAdapter.handle(handlerMapping, request, response);
            modelAndView.render(request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }
}
