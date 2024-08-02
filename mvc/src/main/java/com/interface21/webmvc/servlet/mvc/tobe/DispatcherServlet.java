package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.tobe.config.HandlerScanConfig;
import com.interface21.webmvc.servlet.mvc.tobe.config.ValueConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerAdaptor handlerAdaptor;

    public DispatcherServlet() {

    }

    @Override
    public void init() {
        ValueConfig valueConfig = new ValueConfig();
        HandlerScanConfig handlerScanConfig = new HandlerScanConfig(valueConfig);
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping(
                valueConfig,
                handlerScanConfig.getBasePackages().toArray()
        );
        this.handlerAdaptor = new HandlerAdaptor(List.of(
                annotationHandlerMapping
        ));
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        log.debug("Method : {}, Request URI : {}", request.getMethod(), request.getRequestURI());

        try {
            final var modelAndView = handlerAdaptor.execute(request, response);
            modelAndView.getView().render(modelAndView.getModel(), request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }
}
