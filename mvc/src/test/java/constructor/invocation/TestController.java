package constructor.invocation;

import com.interface21.context.stereotype.Controller;

@Controller
public class TestController {

    public TestController() {
        throw new RuntimeException();
    }
}
