package camp.nextstep.controller;

import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;

@Controller
public class RegisterController {
    @RequestMapping(value = "/register/view", method = RequestMethod.GET)
    public ModelAndView show() {
        return new ModelAndView("/register.jsp");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView save(final User user) {
        InMemoryUserDao.save(user);
        
        return new ModelAndView("redirect:/index.jsp");
    }
}
