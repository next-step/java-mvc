package camp.nextstep.fixtures;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;

@Controller
public class AnnotatedMockController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        System.out.println("AnnotatedMockController.test");
    }
}
