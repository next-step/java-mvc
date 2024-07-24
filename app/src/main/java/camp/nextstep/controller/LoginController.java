package camp.nextstep.controller;

import camp.nextstep.domain.User;
import camp.nextstep.dao.InMemoryUserDao;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView doLogin(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        if (UserSession.isLoggedIn(req.getSession())) {
            return ModelAndView.ofJspView("redirect:/index.jsp");
        }

        final var user = InMemoryUserDao.findByAccount(req.getParameter("account"));
        if (user == null) {
            return ModelAndView.ofJspView("redirect:/401.jsp");
        }

        log.info("User : {}", user);
        return ModelAndView.ofJspView(login(req, user));
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView viewLogin(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        return ModelAndView.ofJspView(getPath(req));
    }

    private static String getPath(HttpServletRequest req) {
        return UserSession.getUserFrom(req.getSession())
                .map(user -> {
                    log.info("logged in {}", user.getAccount());
                    return "redirect:/index.jsp";
                })
                .orElse("/login.jsp");
    }

    private String login(final HttpServletRequest request, final User user) {
        if (user.checkPassword(request.getParameter("password"))) {
            final var session = request.getSession();
            session.setAttribute(UserSession.SESSION_KEY, user);
            return "redirect:/index.jsp";
        }
        return "redirect:/401.jsp";
    }
}
