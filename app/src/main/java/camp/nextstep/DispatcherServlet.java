package camp.nextstep;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.*;
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

    public DispatcherServlet(Object... basePackage) {
        this.handlerMappingRegistry = new HandlerMappingRegistry(new AnnotationHandlerMapping(basePackage));
        this.handlerAdapterRegistry = new HandlerAdapterRegistry(new AnnotationMethodHandlerAdapter());
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.add(handlerAdapter);
    }

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.add(handlerMapping);
    }

    @Override
    public void init() {
        // 메뉴얼 핸들러 추가
        ManualHandlerMapping manualHandlerMapping = new ManualHandlerMapping();
        manualHandlerMapping.initialize();
        addHandlerMapping(manualHandlerMapping);

        // 어노테이션 핸들러 추가
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("camp.nextstep.controller");
        annotationHandlerMapping.initialize();
        addHandlerMapping(annotationHandlerMapping);

        // 핸들러 어댑터 추가
        addHandlerAdapter(new AnnotationMethodHandlerAdapter());
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            Object handler = handlerMappingRegistry.getHandler(request);
            ModelAndView modelAndView = handlerAdapterRegistry.handle(handler, request, response);
            modelAndView.renderView(request, response);
        } catch (final Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }
}
