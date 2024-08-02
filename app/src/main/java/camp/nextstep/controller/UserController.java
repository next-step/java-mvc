package camp.nextstep.controller;

import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JsonView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// jsonView 테스트 컨트롤러
@Controller
public class UserController {

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public ModelAndView getUserByAccount(final HttpServletRequest req, final HttpServletResponse res) {
    if (req.getQueryString() == null) {
      return new ModelAndView(new JspView("redirect:/404.jsp"));
    }

    User user = InMemoryUserDao.findByAccount("gugu");
    ModelAndView modelAndView = new ModelAndView(new JsonView());
    modelAndView.addObject("account", user.getAccount());
    modelAndView.addObject("email", user.getEmail());

    return modelAndView;
  }
}
