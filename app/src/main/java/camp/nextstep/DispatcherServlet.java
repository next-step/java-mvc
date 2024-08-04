package camp.nextstep;

import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.asis.Controller;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
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

  private ManualHandlerMapping manualHandlerMapping;
  private AnnotationHandlerMapping annotationHandlerMapping;

  public DispatcherServlet() {
  }

  @Override
  public void init() {
    this.manualHandlerMapping = new ManualHandlerMapping();
    this.annotationHandlerMapping = new AnnotationHandlerMapping("camp.nextstep");
    this.manualHandlerMapping.initialize();
    this.annotationHandlerMapping.initialize();
  }

  @Override
  protected void service(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException {
    final String requestURI = request.getRequestURI();
    log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

    try {
      final var controller = (Controller) manualHandlerMapping.getHandler(request);

      final var modelAndView = controller.execute(request, response);

      View view = modelAndView.getView();
      view.render(modelAndView.getModel(), request, response);
    } catch (Throwable e) {
      log.error("Exception : {}", e.getMessage(), e);
      throw new ServletException(e.getMessage());
    }
  }

}
