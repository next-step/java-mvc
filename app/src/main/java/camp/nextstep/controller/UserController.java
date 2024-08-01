package camp.nextstep.controller;

import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public ModelAndView show(final String account) {
        return doShow(account);
    }

    private static ModelAndView doShow(String account) {
        log.debug("user id: {}", account);

        ModelAndView modelAndView = new ModelAndView("jsonView");

        if (account == null) {
            return modelAndView;
        }

        final User user = InMemoryUserDao.findByAccount(account);
        if (user == null) {
            return modelAndView;
        }

        return modelAndView.addObject("user", user);
    }

    @RequestMapping(value = "/api/user/{account}", method = RequestMethod.GET)
    public ModelAndView showByPathVariable(@PathVariable("account") final String account) {
        return doShow(account);
    }
}
