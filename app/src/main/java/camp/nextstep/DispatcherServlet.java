package camp.nextstep;

import com.interface21.core.util.ReflectionUtils;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerMapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_BASE_PACKAGE = "camp.nextstep";
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlers = new ArrayList<>();

    public DispatcherServlet() {
    }

    @Override
    public void init() {
        var manualHandlerMapping = new ManualHandlerMapping();
        manualHandlerMapping.initialize();
        var annotationHandlerMapping = new AnnotationHandlerMapping(DEFAULT_BASE_PACKAGE);
        annotationHandlerMapping.initialize();

        this.handlers.add(manualHandlerMapping);
        this.handlers.add(annotationHandlerMapping);
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            execute(request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }

    private void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Optional<HandlerMapping> handler = handlers.stream()
                .filter(it -> it.supports(request))
                .findFirst();

        if (handler.isPresent()) {
            handler.get().adapt(handler.get().getHandler(request), request, response);
        }
    }
}
