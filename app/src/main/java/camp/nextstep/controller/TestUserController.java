package camp.nextstep.controller;

import camp.nextstep.domain.TestUSer;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class TestUserController {
    private static final Logger logger = LoggerFactory.getLogger(TestUserController.class);

    @RequestMapping(value = "/resolvers/string", method = RequestMethod.POST)
    public ModelAndView create_string(String userId, String password) {
        logger.debug("userId: {}, password: {}", userId, password);

        final ModelAndView mv = new ModelAndView(new JsonView());
        mv.addObject("userId", userId);
        mv.addObject("password", password);

        return mv;
    }

    @RequestMapping(value = "/resolvers/primitive/number", method = RequestMethod.POST)
    public ModelAndView create_int_long(long id, int age) {
        logger.debug("id: {}, age: {}", id, age);

        final ModelAndView mv = new ModelAndView(new JsonView());
        mv.addObject("id", id);
        mv.addObject("age", age);

        return mv;
    }

    @RequestMapping(value = "/resolvers/object", method = RequestMethod.POST)
    public ModelAndView create_javabean(TestUSer testUser) {
        logger.debug("testUser: {}", testUser);

        final ModelAndView mv = new ModelAndView(new JsonView());
        mv.addObject("userId", testUser.getUserId());
        mv.addObject("password", testUser.getPassword());
        mv.addObject("age", testUser.getAge());

        return mv;
    }

    @RequestMapping(value = "/resolvers/path/{id}", method = RequestMethod.GET)
    public ModelAndView show_pathvariable(@PathVariable long id) {
        logger.debug("userId: {}", id);

        final ModelAndView mv = new ModelAndView(new JsonView());
        mv.addObject("id", id);

        return mv;
    }
}
