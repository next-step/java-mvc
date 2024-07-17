package camp.nextstep.controller;

import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login/view", method = RequestMethod.GET)
    public ModelAndView getLoginPage(final HttpServletRequest req, final HttpServletResponse res) {
        return ModelAndView.ofJspView(getPath(req));
    }

    private String getPath(final HttpServletRequest req) {
        return UserSession.getUserFrom(req.getSession())
                .map(user -> {
                    log.info("logged in {}", user.getAccount());
                    return "redirect:/index.jsp";
                })
                .orElse("/login.jsp");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView processLogin(final HttpServletRequest req, final HttpServletResponse res) {
        if (UserSession.isLoggedIn(req.getSession())) {
            return ModelAndView.ofJspView("redirect:/index.jsp");
        }

        final var user = InMemoryUserDao.findByAccount(req.getParameter("account"));
        if (user == null) {
            return ModelAndView.ofJspView("redirect:/401.jsp");
        }

        log.info("User : {}", user);
        return login(req, user);
    }

    private ModelAndView login(final HttpServletRequest request, final User user) {
        if (user.checkPassword(request.getParameter("password"))) {
            final var session = request.getSession();
            session.setAttribute(UserSession.SESSION_KEY, user);
            return ModelAndView.ofJspView("redirect:/index.jsp");
        }
        return ModelAndView.ofJspView("redirect:/401.jsp");
    }
}
