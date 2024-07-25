package camp.nextstep.controller;

import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.view.JspView;
import com.interface21.webmvc.servlet.view.ViewUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RegisterController {
    @RequestMapping(value = "/register/view", method = RequestMethod.GET)
    public ModelAndView show(final HttpServletRequest req, final HttpServletResponse res) {
        View view = ViewUtil.createViewByViewName("/register.jsp");

        return new ModelAndView(view);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView save(final HttpServletRequest req, final HttpServletResponse res) {
        final var user = new User(2,
                req.getParameter("account"),
                req.getParameter("password"),
                req.getParameter("email"));
        InMemoryUserDao.save(user);

        return new ModelAndView(new JspView("redirect:/index.jsp"));
    }
}
