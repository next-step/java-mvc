package samples;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/get-test", method = RequestMethod.GET)
    public ModelAndView findUserId(String userId) {
        log.info("test controller get method");
        final var modelAndView = ModelAndView.jspView("/get-test.jsp");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @RequestMapping(value = "/get-test/{userId}", method = RequestMethod.GET)
    public ModelAndView findUserIdWithPath(@PathVariable(value = "userId") String userId) {
        log.info("test controller get method with path");
        final var modelAndView = ModelAndView.jspView("/get-test.jsp");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @RequestMapping(value = "/post-test", method = RequestMethod.POST)
    public ModelAndView save(TestUser testUser) {
        log.info("test controller post method");
        final var modelAndView = ModelAndView.jspView("post-test.jsp");
        modelAndView.addObject("userId", testUser.getUserId());
        modelAndView.addObject("password", testUser.getPassword());
        modelAndView.addObject("age", testUser.getAge());
        return modelAndView;
    }
}
