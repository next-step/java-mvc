package samples;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping(value = "/prefix")
@Controller
public class WithClassRequestMappingAnnotation {

    private static final Logger log = LoggerFactory.getLogger(WithClassRequestMappingAnnotation.class);

    @RequestMapping(value = "/get-test", method = RequestMethod.GET)
    public ModelAndView findUserId(final HttpServletRequest request, final HttpServletResponse response) {
        log.info("WithClassRequestMappingAnnotation controller get method");
        final var modelAndView = new ModelAndView(new JspView(""));
        modelAndView.addObject("name", request.getAttribute("name"));
        return modelAndView;
    }
}
