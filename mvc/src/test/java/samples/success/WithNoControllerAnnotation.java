package samples.success;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WithNoControllerAnnotation {

    private static final Logger log = LoggerFactory.getLogger(WithNoControllerAnnotation.class);

    @RequestMapping(value = "/test-no-annotation", method = RequestMethod.POST)
    public ModelAndView create_string(String userId, String password) {
        log.info("@Controller 애노테이션이 없어서 호출되지 않음");
        return new ModelAndView(new JsonView());
    }
}
