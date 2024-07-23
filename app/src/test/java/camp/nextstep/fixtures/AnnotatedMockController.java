package camp.nextstep.fixtures;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

@Controller
public class AnnotatedMockController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        System.out.println("AnnotatedMockController.test");
    }
}
