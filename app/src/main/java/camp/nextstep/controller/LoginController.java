package camp.nextstep.controller;

import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login/view", method = RequestMethod.GET)
    public ModelAndView show(final HttpSession session) {
        return UserSession.getUserFrom(requireNonNull(session))
                          .map(user -> {
                              log.info("logged in {}", user.getAccount());
                              return new ModelAndView("redirect:/index.jsp");
                          })
                          .orElse(new ModelAndView("/login.jsp"));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(final HttpSession session, final String account, final String password) {
        if (UserSession.isLoggedIn(requireNonNull(session))) {
            return new ModelAndView("redirect:/index.jsp");
        }

        final var user = InMemoryUserDao.findByAccount(account);
        if (user == null) {
            return new ModelAndView("redirect:/401.jsp");
        }

        log.info("User : {}", user);
        return login(session, user, password);
    }

    private ModelAndView login(final HttpSession session, final User user, final String password) {
        if (user.checkPassword(password)) {
            session.setAttribute(UserSession.SESSION_KEY, user);
            return new ModelAndView("redirect:/index.jsp");
        }
        return new ModelAndView("redirect:/401.jsp");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(final HttpSession session) {
        requireNonNull(session).removeAttribute(UserSession.SESSION_KEY);

        return new ModelAndView("redirect:/");
    }
}
