package camp.nextstep.controller;

import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RegisterController {

    @RequestMapping(value = "/register/view", method = RequestMethod.GET)
    public ModelAndView getRegisterPage(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        return ModelAndView.ofJspView("/register.jsp");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegister(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        final var user = new User(2,
                req.getParameter("account"),
                req.getParameter("password"),
                req.getParameter("email"));
        InMemoryUserDao.save(user);

        return ModelAndView.ofJspView("redirect:/index.jsp");
    }
}
