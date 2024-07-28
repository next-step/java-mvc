package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerAdapter;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerAdapterRegistry;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerMappingRegistry;
import com.interface21.webmvc.servlet.mvc.tobe.JspViewResolver;
import com.interface21.webmvc.servlet.mvc.tobe.ViewResolver;
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
    private final HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();
    private final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();
    private ViewResolver viewResolver;

    public DispatcherServlet(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void init() {
        AnnotationHandlerMapping basePackageHandlerMapping = new AnnotationHandlerMapping(basePackage);
        basePackageHandlerMapping.initialize();
        handlerMappingRegistry.addHandlerMapping(basePackageHandlerMapping);
        handlerAdapterRegistry.addHandlerAdapter(new AnnotationHandlerAdapter());
        // 초기 주입에 따라 변경 가능하도록 작성
        viewResolver = new JspViewResolver();
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            final var controller = handlerMappingRegistry.getHandler(request);
            final var Object = handlerAdapterRegistry.getHandlerAdapter(controller)
                .handle(request, response, controller);

            move(Object, request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }

    private void move(final Object object, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        if (object instanceof String) {
            modelAndView = new ModelAndView(viewResolver.resolveViewName((String) object));
        }
        if (object instanceof ModelAndView) {
            modelAndView = (ModelAndView) object;
            modelAndView.getView().render(modelAndView.getModel(), request, response);
        }

        if (modelAndView == null) {
            throw new IllegalStateException("지원되지 않는 핸들러입니다.");
        }

        modelAndView.getView().render(modelAndView.getModel(), request, response);
    }
}
