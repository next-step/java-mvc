package camp.nextstep;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.asis.Controller;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerMapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.Serial;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;

    public DispatcherServlet() {
    }

    @Override
    public void init() {
        handlerMappings = List.of(
                new ManualHandlerMapping(),
                new AnnotationHandlerMapping("camp.nextstep")
        );

        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            // 핸들러를 가져온다
            // manual - Controller를, annotation - HandlerExecution을 반환한다
            List<Object> handlers = handlerMappings.stream()
                    .map(handlerMapping -> handlerMapping.getHandler(request))
                    .filter(Objects::nonNull)
                    .toList();

            // 핸들러를 실행한다
            List<ModelAndView> modelAndViews = handlers.stream()
                    .map(handler -> {
                        try {
                            if (handler instanceof Controller controller) {
                                return controller.execute(request, response);
                            }

                            if (handler instanceof HandlerExecution handlerExecution) {
                                return handlerExecution.handle(request, response);
                            }
                            throw new IllegalArgumentException("지원하지 않는 handler");
                        } catch (Exception e) {
                            throw new RuntimeException();
                        }
                    })
                    .toList();

            modelAndViews.forEach(modelAndView -> {
                try {
                    View view = modelAndView.getView();
                    view.render(modelAndView.getModel(), request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }
}
