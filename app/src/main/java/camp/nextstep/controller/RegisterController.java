package camp.nextstep.controller;

import camp.nextstep.domain.User;
import camp.nextstep.dao.InMemoryUserDao;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RegisterController {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView doRegister(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        final var user = new User(2,
                req.getParameter("account"),
                req.getParameter("password"),
                req.getParameter("email"));
        InMemoryUserDao.save(user);

        return ModelAndView.ofView("redirect:/index.jsp");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView viewRegister(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        return ModelAndView.ofView("/register.jsp");
    }
}
