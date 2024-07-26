package samples.success;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.ModelAttribute;
import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestBody;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.web.bind.annotation.RequestParam;
import com.interface21.webmvc.servlet.mvc.ModelAndView;
import com.interface21.webmvc.servlet.mvc.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class TestUserController {

    private static final Logger log = LoggerFactory.getLogger(TestUserController.class);

    @RequestMapping(value = "/users-string", method = RequestMethod.GET)
    public ModelAndView create_string(@RequestParam String userId, @RequestParam String password) {
        log.debug("userId: {}, password: {}", userId, password);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("userId", userId);
        mav.addObject("password", password);
        return mav;
    }

    @RequestMapping(value = "/users-number", method = RequestMethod.GET)
    public ModelAndView create_int_long(@RequestParam long id, @RequestParam int age) {
        log.debug("id: {}, age: {}", id, age);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("id", id);
        mav.addObject("age", age);
        return mav;
    }

    @RequestMapping(value = "/users-model", method = RequestMethod.GET)
    public ModelAndView create_javabean1(@ModelAttribute TestUser testUser) {
        log.debug("testUser: {}", testUser);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("testUser", testUser);
        return mav;
    }

    @RequestMapping(value = "/users-model", method = RequestMethod.POST)
    public ModelAndView create_javabean2(@ModelAttribute TestUser testUser) {
        log.debug("testUser: {}", testUser);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("testUser", testUser);
        return mav;
    }

    @RequestMapping(value = "/users-json", method = RequestMethod.POST)
    public ModelAndView create_javabean3(@RequestBody TestUser testUser) {
        log.debug("testUser: {}", testUser);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("testUser", testUser);
        return mav;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView show_pathvariable(@PathVariable long id) {
        log.debug("userId: {}", id);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("id", id);
        return mav;
    }
}
