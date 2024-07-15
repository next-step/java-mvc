package camp.nextstep;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.asis.Controller;
import com.interface21.webmvc.servlet.mvc.tobe.annotation.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.annotation.HandlerExecution;
import com.interface21.webmvc.servlet.view.JspView;
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

    public DispatcherServlet() {
        this.handlerMappingRegistry = new HandlerMappingRegistry(new ManualHandlerMapping());
    }

    public DispatcherServlet(Object... basePackage) {
        this.handlerMappingRegistry = new HandlerMappingRegistry(
                new ManualHandlerMapping(),
                new AnnotationHandlerMapping(basePackage)
        );
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
            final ModelAndView modelAndView = handle(request, response);
            modelAndView.render(request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }

    private ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final var handler = handlerMappingRegistry.getHandler(request);
        if (handler instanceof Controller) {
            final var viewName = ((Controller) handler).execute(request, response);
            return new ModelAndView(new JspView(viewName));
        }
        if (handler instanceof HandlerExecution) {
            return ((HandlerExecution) handler).handle(request, response);
        }
        throw new IllegalStateException("지원하는 handler가 없습니다.");
    }
}
