package camp.nextstep.controller;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;

@Controller
public class ForwardController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView get() {
        return new ModelAndView("/index.jsp");
    }
}
