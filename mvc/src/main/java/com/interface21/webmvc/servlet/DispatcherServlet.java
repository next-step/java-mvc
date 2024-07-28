package com.interface21.webmvc.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String BASE_PACKAGE = "camp.nextstep";
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HttpRequestHandlerMappingRegistry requestHandlers = new HttpRequestHandlerMappingRegistry();
    private final ExceptionHandlerRegistry exceptionHandlers = new ExceptionHandlerRegistry();
    private final HandlerAdapterRegistry handlerAdapters = new HandlerAdapterRegistry();

    public DispatcherServlet() {
        requestHandlers.addHandlerMapping(new ControllerHandlerMapping(BASE_PACKAGE));
        exceptionHandlers.addHandlerMapping(new ControllerAdviceHandlerMapping(BASE_PACKAGE));
        handlerAdapters.addAdapter(new RequestHandlerAdapter());
    }

    @Override
    public void init() {
        requestHandlers.initialize();
        exceptionHandlers.initialize();
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            final var controller = requestHandlers.getHandler(request);
            handlerAdapters.handle(request, response, controller);
        } catch (Throwable e) {
            handleException(e, request, response);
        }
    }

    private void handleException(final Throwable e, final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        log.error("Exception : {}", e.getMessage(), e);
        var handler = exceptionHandlers.getHandler(e.getClass());
        if (handler == null) {
            throw new ServletException(e.getMessage());
        }
        handler.handle(e, request, response);
    }
}
