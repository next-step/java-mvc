package samples;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public record TestRecord() {
    @RequestMapping(value = "/record/get", method = RequestMethod.GET)
    public ModelAndView get(final HttpServletRequest request, final HttpServletResponse response) {
        return new ModelAndView("jsonView").addObject("ok", true);
    }
}
