package camp.nextstep;

import camp.nextstep.controller.LoginController;
import camp.nextstep.controller.LoginViewController;
import camp.nextstep.controller.LogoutController;
import camp.nextstep.controller.RegisterController;
import camp.nextstep.controller.RegisterViewController;
import com.interface21.webmvc.servlet.mvc.asis.Controller;
import com.interface21.webmvc.servlet.mvc.asis.ForwardController;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerMapping;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManualHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(ManualHandlerMapping.class);

    private final Map<String, Controller> controllers = new HashMap<>();

    public void initialize() {
        controllers.put("/", new ForwardController("/index.jsp"));
        controllers.put("/login", new LoginController());
        controllers.put("/login/view", new LoginViewController());
        controllers.put("/logout", new LogoutController());

        log.info("Initialized Handler Mapping!");
        controllers.keySet()
            .forEach(path -> log.info("Path : {}, Controller : {}", path,
                controllers.get(path).getClass()));
    }

    @Override
    public Object getHandler(HttpServletRequest httpServletRequest) {
        log.debug("Request Mapping Uri : {}", httpServletRequest.getRequestURI());
        return controllers.get(httpServletRequest.getRequestURI());
    }
}
