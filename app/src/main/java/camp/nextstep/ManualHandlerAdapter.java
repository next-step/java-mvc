package camp.nextstep;

import com.interface21.webmvc.servlet.mvc.asis.Controller;

public class ManualHandlerAdapter {

    public boolean accept(Object handler) {
        return handler instanceof Controller;
    }
}
