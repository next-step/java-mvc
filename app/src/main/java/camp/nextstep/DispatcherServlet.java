package camp.nextstep;

import com.interface21.webmvc.servlet.mvc.tobe.annotation.AnnotationHandlerAdapter;
import com.interface21.webmvc.servlet.mvc.tobe.annotation.AnnotationHandlerMapping;
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
        this.handlerMappingRegistry = new HandlerMappingRegistry(new AnnotationHandlerMapping("camp"));
        this.handlerAdapterRegistry = new HandlerAdapterRegistry(new AnnotationHandlerAdapter());
    }

    public DispatcherServlet(Object... basePackage) {
        this.handlerMappingRegistry = new HandlerMappingRegistry(new AnnotationHandlerMapping(basePackage));
        this.handlerAdapterRegistry = new HandlerAdapterRegistry(new AnnotationHandlerAdapter());
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
            final var handler = handlerMappingRegistry.getHandler(request);
            final var modelAndView = handlerAdapterRegistry.handle(handler, request, response);
            modelAndView.render(request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }
}
