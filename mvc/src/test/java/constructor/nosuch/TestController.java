package constructor.nosuch;

import com.interface21.context.stereotype.Controller;

@Controller
public class TestController {

    private final int value;

    public TestController(int value) {
        this.value = value;
    }
}
