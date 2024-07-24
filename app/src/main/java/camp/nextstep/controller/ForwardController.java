package camp.nextstep.controller;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.mvc.ModelAndView;
import com.interface21.webmvc.servlet.mvc.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ForwardController {
    @RequestMapping("/")
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) {
        return new ModelAndView(new JspView("/index.jsp"));
    }
}
