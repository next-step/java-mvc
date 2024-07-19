package camp.nextstep;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private ManualHandlerMapping manualHandlerMapping;
    private AnnotationHandlerMapping annotationHandlerMapping;

    public DispatcherServlet() {
    }

    @Override
    public void init() {
        this.manualHandlerMapping = new ManualHandlerMapping();
        this.manualHandlerMapping.initialize();

        this.annotationHandlerMapping = new AnnotationHandlerMapping("camp.nextstep.controller");
        this.annotationHandlerMapping.initialize();
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            HandlerExecution handlerExecution = annotationHandlerMapping.getHandler(request);
            if (handlerExecution != null) {
                ModelAndView modelAndView = handlerExecution.handle(request, response);
                modelAndView.getView().render(modelAndView.getModel(), request, response);
                return;
            }

            final var controller = manualHandlerMapping.getHandler(requestURI);
            final var viewName = controller.execute(request, response);

            JspView jspView = new JspView(viewName);
            jspView.render(Collections.emptyMap(), request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new RuntimeException(e);
            //            throw new ServletException(e.getMessage());
        }
    }
}
