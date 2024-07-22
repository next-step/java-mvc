package camp.nextstep.controller;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.interface21.webmvc.servlet.mvc.asis.Controller;

public class LogoutController implements Controller {

    @Override
    public ModelAndView execute(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        final var session = req.getSession();
        session.removeAttribute(UserSession.SESSION_KEY);
        return ModelAndView.ofJspView("redirect:/");
    }
}
