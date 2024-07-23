package camp.nextstep;

import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerAdaptor;
import com.interface21.webmvc.servlet.mvc.tobe.config.HandlerScanConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private ManualHandlerMapping manualHandlerMapping;
    private AnnotationHandlerMapping annotationHandlerMapping;
    private HandlerAdaptor handlerAdaptor;
    private HandlerScanConfig handlerScanConfig;

    public DispatcherServlet() {
        handlerScanConfig = new HandlerScanConfig();
    }

    @Override
    public void init() {
        this.manualHandlerMapping = new ManualHandlerMapping();
        this.manualHandlerMapping.initialize();
        this.annotationHandlerMapping = new AnnotationHandlerMapping(
                handlerScanConfig.getBasePackages().toArray()
        );
        this.annotationHandlerMapping.initialize();
        this.handlerAdaptor = new HandlerAdaptor(List.of(
                manualHandlerMapping,
                annotationHandlerMapping
        ));
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        log.debug("Method : {}, Request URI : {}", request.getMethod(), request.getRequestURI());

        try {
            final var modelAndView = handlerAdaptor.execute(request, response);
            modelAndView.getView().render(new HashMap<>(), request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }
}
