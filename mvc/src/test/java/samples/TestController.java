package samples;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Controller
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/get-test", method = RequestMethod.GET)
    public ModelAndView findUserId(final HttpServletRequest request, final HttpServletResponse response) {
        log.info("test controller get method");

        return new ModelAndView("")
                .addObject("id", request.getAttribute("id"));
    }

    @RequestMapping(value = "/post-test", method = RequestMethod.POST)
    public ModelAndView save(final HttpServletRequest request, final HttpServletResponse response) {
        log.info("test controller post method");

        return new ModelAndView("")
                .addObject("id", request.getAttribute("id"));
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest request, HttpServletResponse response) {
        final String account = request.getParameter("account");
        log.debug("user id : {}", account);

        return new ModelAndView("jsonView")
                .addObject("user", Map.of("a", "b"));
    }

    @RequestMapping(value = "/json-post-test", method = RequestMethod.POST)
    public ModelAndView postAndReturn(final HttpServletRequest request, final HttpServletResponse response) {
        return new ModelAndView("jsonView")
                .addObject("user", Map.of("c", "d"))
                .addObject("age", 14);
    }
}
