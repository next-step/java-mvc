package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.mvc.tobe.HandlerAdapterRegistry;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerMappingRegistry;
import com.interface21.webmvc.servlet.mvc.tobe.ViewResolvers;
import com.interface21.webmvc.servlet.mvc.tobe.WebConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final String basePackage;
    private HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();
    private HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();
    private ViewResolvers viewResolvers;

    public DispatcherServlet(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void init() {
        WebConfig webConfig = new WebConfig();
        webConfig.init(basePackage);
        handlerMappingRegistry = webConfig.handlerMappingRegistry();
        handlerAdapterRegistry = webConfig.handlerAdapterRegistry();
        viewResolvers = webConfig.viewResolvers();
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            final var controller = handlerMappingRegistry.getHandler(request);
            final var handlerResult = handlerAdapterRegistry.getHandlerAdapter(controller)
                .handle(request, response, controller);

            move(handlerResult, request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }

    private void move(final Object handlerResult, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        viewResolvers.resolveAndRenderView(handlerResult, request, response);
    }


}
