package camp.nextstep;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.AdapterRegistry;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerAdapter;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerAdapter;
import com.interface21.webmvc.servlet.mvc.tobe.MappingRegistry;
import com.interface21.webmvc.servlet.mvc.tobe.exception.NotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private ManualHandlerMapping manualHandlerMapping;
    private AnnotationHandlerMapping annotationHandlerMapping;
    private MappingRegistry mappingRegistry;
    private AdapterRegistry adapterRegistry;

    public DispatcherServlet(final Object... basePackage) {
        this.manualHandlerMapping = new ManualHandlerMapping();
        this.annotationHandlerMapping = new AnnotationHandlerMapping(basePackage);
    }

    @Override
    public void init() {
        this.manualHandlerMapping.initialize();
        this.annotationHandlerMapping.initialize();

        this.mappingRegistry = new HandlerMappingRegistry(
            List.of(this.manualHandlerMapping, this.annotationHandlerMapping));
        this.adapterRegistry = new HandlerAdapterRegistry(
            List.of(new ManualHandlerAdapter(), new AnnotationHandlerAdapter()));
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
        throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            final Object handler = mappingRegistry.getHandler(request)
                .orElseThrow(() -> new NotFoundException("Path Not found"));
            final HandlerAdapter handlerAdapter = adapterRegistry.getHandlerAdapter(handler)
                .orElseThrow(() -> new IllegalStateException("Handler Not Found"));

            ModelAndView modelAndView = handlerAdapter.handle(handler, request, response);
            modelAndView.render(request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }

}
