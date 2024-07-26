package camp.nextstep;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.interface21.webmvc.servlet.mvc.tobe.*;

import camp.nextstep.controller.ForwardController;

public class ManualHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(ManualHandlerMapping.class);

    private final Map<String, HandlerExecution> handlers = new HashMap<>();

    public void initialize() {
        logging(loadHandlerMapping());
    }

    @Override
    public boolean supports(HttpServletRequest request) {
        return handlers.containsKey(request.getRequestURI());
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        final var requestURI = request.getRequestURI();
        log.debug("Request Mapping Uri : {}", requestURI);
        return handlers.get(requestURI);
    }

    private Consumer<Map<String, HandlerExecution>> loadHandlerMapping() {
        return controllers -> {
            var controller = new ForwardController("/index.jsp");
            var method =
                    MethodScanner.scanMethod(
                            controller.getClass(),
                            "execute",
                            HttpServletRequest.class,
                            HttpServletResponse.class);
            controllers.put("/", new HandlerExecution(controller, method));
        };
    }

    private void logging(Consumer<Map<String, HandlerExecution>> consumer) {
        log.info("Initialized ManualHandlerMapping");
        consumer.andThen(
                        handlers ->
                                handlers.keySet()
                                        .forEach(
                                                path ->
                                                        log.info(
                                                                "Path : {}, Controller : {}",
                                                                path,
                                                                this.handlers.get(path).getClass())))
                .accept(handlers);
    }
}
