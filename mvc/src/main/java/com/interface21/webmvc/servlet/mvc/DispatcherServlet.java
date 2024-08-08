package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();
    private final RequestHandlerAdapter handlerAdapter = new RequestHandlerAdapter();

    public DispatcherServlet(Object... basePackage) {
        handlerMappingRegistry.addHandlerMapping(new AnnotationHandlerMapping(basePackage));
        // default resolver 추가
        handlerAdapter.addArgumentResolvers(handlerAdapter.getDefaultArgumentResolvers());
    }

    @Override
    public void init() {
        handlerMappingRegistry.initialize();
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            final var handler = handlerMappingRegistry.getHandler(request);
            handlerAdapter.handle(request, response, handler);
        } catch (Throwable e) {
            handlerException(e, request, response);
        }
    }

    private static void handlerException(Throwable e, HttpServletRequest request, HttpServletResponse response) {
        log.error("Exception : {}", e.getMessage(), e);

        ModelAndView modelAndView = new ModelAndView(new JspView("redirect:/500.jsp"));
        try {
          modelAndView.getView().render(modelAndView.getModel(), request, response);
        } catch (Exception ex) {
          throw new RuntimeException(ex);
        }
    }
}
