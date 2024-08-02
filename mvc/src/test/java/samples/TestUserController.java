package samples;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.web.bind.annotation.ResponseBody;
import com.interface21.webmvc.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class TestUserController {

    private static final Logger log = LoggerFactory.getLogger(TestUserController.class);

    @ResponseBody
    @RequestMapping(value = "/users-create-string", method = RequestMethod.POST)
    public ModelAndView create_string(String userId, String password) {
        log.debug("userId: {}, password: {}", userId, password);

        return new ModelAndView(null)
                .addObject("userId", userId)
                .addObject("password", password);
    }

    @ResponseBody
    @RequestMapping(value = "/users-create-int-long", method = RequestMethod.POST)
    public ModelAndView create_int_long(long id, int age) {
        log.debug("id: {}, age: {}", id, age);

        return new ModelAndView(null)
                .addObject("id", id)
                .addObject("age", age);
    }

    @RequestMapping(value = "/users-create-javabean", method = RequestMethod.POST)
    public ModelAndView create_javabean(TestUser testUser) {
        log.debug("testUser: {}", testUser);

        return new ModelAndView("jsonView")
                .addObject("testUser", testUser);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView show_pathvariable(@PathVariable long id) {
        log.debug("userId: {}", id);

        return new ModelAndView("jsonView")
                .addObject("id", id);
    }
}
