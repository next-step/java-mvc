package camp.nextstep.controller;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;

@Controller
public class AnnotationForwardController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView forward() {
        return new ModelAndView(new JspView("/index.jsp"));
    }
}
