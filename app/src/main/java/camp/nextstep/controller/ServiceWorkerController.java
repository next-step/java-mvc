package camp.nextstep.controller;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ServiceWorkerController {

    private static final Logger log = LoggerFactory.getLogger(ServiceWorkerController.class);

    @RequestMapping(value = "/service-worker.js", method = RequestMethod.GET)
    public ModelAndView method(final HttpServletRequest request, final HttpServletResponse response) {
        log.info("requestURI={}", request.getRequestURI());
        return new ModelAndView(new JspView("/"));
    }
}
