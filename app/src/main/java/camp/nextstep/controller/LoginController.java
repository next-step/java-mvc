package camp.nextstep.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.interface21.webmvc.servlet.mvc.asis.Controller;

import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;

public class LoginController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    public String execute(final HttpServletRequest req, final HttpServletResponse res)
            throws Exception {
        if (UserSession.isLoggedIn(req.getSession())) {
            return "redirect:/index.jsp";
        }

        final var user = InMemoryUserDao.findByAccount(req.getParameter("account"));
        if (user == null) {
            return "redirect:/401.jsp";
        }

        log.info("User : {}", user);
        return login(req, user.get());
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
