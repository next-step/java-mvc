package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final ControllerScanner controllerScanner;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.controllerScanner = new ControllerScanner(basePackage);
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        for (Object controller : controllers.values()) {
            registerHandlerMappings(controller);
        }
    }

    private void registerHandlerMappings(Object controller) {
        Set<Method> allMethods = ReflectionUtils.getAllMethods(controller.getClass(), ReflectionUtils.withAnnotation(RequestMapping.class));
        for (Method method : allMethods) {
            registerHandlerMapping(controller, method);
        }
    }

    private void registerHandlerMapping(Object controller, Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            List<HandlerKey> handlerKeys = makeHandlerKeys(requestMapping);
            HandlerExecution handlerExecution = new HandlerExecution(controller, method);
            handlerExecutions.putAll(handlerKeys.stream()
                                                .collect(HashMap::new, (m, v) -> m.put(v, handlerExecution), HashMap::putAll));
        }
    }

    private List<HandlerKey> makeHandlerKeys(RequestMapping requestMapping) {
        String url = requestMapping.value();
        RequestMethod[] methods = requestMapping.method();
        return Arrays.stream(methods)
                     .map(method -> new HandlerKey(url, method))
                     .toList();
    }

    public Object getHandler(final HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        HandlerKey handlerKey = new HandlerKey(requestURI, RequestMethod.valueOf(method));
        return handlerExecutions.get(handlerKey);
    }
}
