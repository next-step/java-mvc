package camp.nextstep.controller;

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

  @RequestMapping(value = "/users", method = RequestMethod.POST)
  public ModelAndView create_string(String userId, String password) {
    logger.debug("userId: {}, password: {}", userId, password);
    return null;
  }

  @RequestMapping(value = "/users", method = RequestMethod.POST)
  public ModelAndView create_int_long(long id, int age) {
    logger.debug("id: {}, age: {}", id, age);

    final ModelAndView modelAndView = new ModelAndView(new JsonView());
    modelAndView.addObject("id", id);
    modelAndView.addObject("age", age);

    return modelAndView;
  }

  @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
  public ModelAndView show_pathvariable(@PathVariable long id) {
    logger.debug("userId: {}", id);

    final ModelAndView modelAndView = new ModelAndView(new JsonView());
    modelAndView.addObject("id", id);

    return modelAndView;
  }
}
