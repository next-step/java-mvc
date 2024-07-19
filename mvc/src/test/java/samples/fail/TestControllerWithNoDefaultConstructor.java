package samples.fail;

import com.interface21.context.stereotype.Controller;

@Controller
public class TestControllerWithNoDefaultConstructor {
    private final String someField;

    public TestControllerWithNoDefaultConstructor(String someField) {
        this.someField = someField;
    }
}
