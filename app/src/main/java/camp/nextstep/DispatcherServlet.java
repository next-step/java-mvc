package camp.nextstep;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.HandlerAdapter;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerAdapters;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerMappings;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.interface21.webmvc.servlet.view.JspView;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappings handlerMappings = new HandlerMappings();
    private final HandlerAdapters handlerAdapters = new HandlerAdapters();

    public DispatcherServlet() {
    }

    @Override
    public void init() {
        initHandlerMappings();
        initHandlerAdapters();
    }

    private void initHandlerMappings() {
        handlerMappings.initialize();

        final ManualHandlerMapping manualHandlerMapping = new ManualHandlerMapping();
        manualHandlerMapping.initialize();

        handlerMappings.addHandlerMapping(manualHandlerMapping);
    }

    private void initHandlerAdapters() {
        handlerAdapters.initialize();
        handlerAdapters.addAdapter(new ManualHandlerAdapter());
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        try {
            final var controller = handlerMappings.getHandler(request);
            final HandlerAdapter handlerAdapter = handlerAdapters.getHandlerAdapter(controller);
            final ModelAndView modelAndView = handlerAdapter.handle(request, response, controller);
            move(modelAndView, request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }

    private void move(final ModelAndView mv, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final View view = mv.getView();
        view.render(mv.getModel(), request, response);
    }
}
