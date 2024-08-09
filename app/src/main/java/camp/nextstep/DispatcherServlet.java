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

    private final Object[] basePackages;
    private MappingRegistry mappingRegistry;
    private AdapterRegistry adapterRegistry;

    public DispatcherServlet(final Object... basePackage) {
        basePackages = basePackage;
    }

    @Override
    public void init() {
        this.mappingRegistry = new HandlerMappingRegistry(
            List.of(new AnnotationHandlerMapping(basePackages)));
        this.adapterRegistry = new HandlerAdapterRegistry(
            List.of(new AnnotationHandlerAdapter()));

        this.mappingRegistry.initialize();
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
