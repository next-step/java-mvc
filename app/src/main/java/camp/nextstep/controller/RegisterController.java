package camp.nextstep.controller;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;

@Controller
public class RegisterController{

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView execute(final HttpServletRequest req, final HttpServletResponse res)
            throws Exception {
        final var user =
                new User(
                        2,
                        req.getParameter("account"),
                        req.getParameter("password"),
                        req.getParameter("email"));
        InMemoryUserDao.save(user);
        return new ModelAndView(new JspView("redirect:/index.jsp"));
    }
}
