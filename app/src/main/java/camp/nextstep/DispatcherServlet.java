package camp.nextstep;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.asis.Controller;
import com.interface21.webmvc.servlet.mvc.tobe.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.interface21.webmvc.servlet.view.JspView;

import java.util.List;

public class DispatcherServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

  private HandlerMappings handlerMappings;
  private HandlerAdapters handlerAdapters;

  public DispatcherServlet() {
  }

  @Override
  public void init() {
    initializeHandlerMappings();
    initializeHandlerAdapters();
  }

  private void initializeHandlerMappings() {
    handlerMappings = HandlerMappings.of(List.of(new ManualHandlerMapping(), new AnnotationHandlerMapping("camp.nextstep")));
    handlerMappings.initialize();
  }

  private void initializeHandlerAdapters() {
    handlerAdapters = HandlerAdapters.of(List.of(new ManualHandlerAdapter(), new AnnotationHandlerAdapter()));
  }

  @Override
  protected void service(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException {
    final String requestURI = request.getRequestURI();
    log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

    try {
      Object handler = handlerMappings.getHandler(request);

      HandlerAdapter handlerAdapter = handlerAdapters.findBy(handler);
      ModelAndView modelAndView = handlerAdapter.handle(handler, request, response);

      View view = modelAndView.getView();
      view.render(modelAndView.getModel(), request, response);
    } catch (Throwable e) {
      log.error("Exception : {}", e.getMessage(), e);
      throw new ServletException(e.getMessage());
    }
  }

}
