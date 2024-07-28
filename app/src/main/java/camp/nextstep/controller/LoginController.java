package camp.nextstep.controller;

import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login/view", method = RequestMethod.GET)
    public ModelAndView show(final HttpServletRequest req, final HttpServletResponse res) {
        HttpSession session = Objects.requireNonNull(req.getSession());
        return UserSession.getUserFrom(session)
                          .map(user -> {
                              log.info("logged in {}", user.getAccount());
                              return new ModelAndView("redirect:/index.jsp");
                          })
                          .orElse(new ModelAndView("/login.jsp"));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(final HttpServletRequest req, final HttpServletResponse res) {
        HttpSession session = Objects.requireNonNull(req.getSession());
        if (UserSession.isLoggedIn(session)) {
            return new ModelAndView("redirect:/index.jsp");
        }

        final var user = InMemoryUserDao.findByAccount(req.getParameter("account"));
        if (user == null) {
            return new ModelAndView("redirect:/401.jsp");
        }

        log.info("User : {}", user);
        return login(req, user);
    }

    private ModelAndView login(final HttpServletRequest request, final User user) {
        if (user.checkPassword(request.getParameter("password"))) {
            final var session = Objects.requireNonNull(request.getSession());
            session.setAttribute(UserSession.SESSION_KEY, user);
            return new ModelAndView("redirect:/index.jsp");
        }
        return new ModelAndView("redirect:/401.jsp");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        final var session = Objects.requireNonNull(req.getSession());
        session.removeAttribute(UserSession.SESSION_KEY);

        return new ModelAndView("redirect:/");
    }
}
