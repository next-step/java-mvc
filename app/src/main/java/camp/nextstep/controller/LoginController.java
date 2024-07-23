package camp.nextstep.controller;

import camp.nextstep.domain.User;
import camp.nextstep.dao.InMemoryUserDao;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.interface21.webmvc.servlet.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    public ModelAndView execute(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        if (UserSession.isLoggedIn(req.getSession())) {
            JspView jspView = new JspView( "redirect:/index.jsp");
            return new ModelAndView(jspView);
        }

        final var user = InMemoryUserDao.findByAccount(req.getParameter("account"));
        if (user == null) {
            JspView jspView = new JspView( "redirect:/401.jsp");
            return new ModelAndView(jspView);
        }

        log.info("User : {}", user);
        JspView jspView = new JspView(login(req, user));
        return new ModelAndView(jspView);
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
