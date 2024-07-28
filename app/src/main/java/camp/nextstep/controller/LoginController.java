package camp.nextstep.controller;

import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String account, String password, HttpServletRequest req) throws Exception {
        if (UserSession.isLoggedIn(req.getSession())) {
            return "redirect:/index";
        }

        final var user = InMemoryUserDao.findByAccount(account);
        if (user == null) {
            return "redirect:/401";
        }

        log.info("User : {}", user);
        return login(req, password, user);
    }

    private String login(final HttpServletRequest request, final String password, final User user) {
        if (user.checkPassword(password)) {
            final var session = request.getSession();
            session.setAttribute(UserSession.SESSION_KEY, user);
            return "redirect:/index";
        }
        return "redirect:/401";
    }
}
