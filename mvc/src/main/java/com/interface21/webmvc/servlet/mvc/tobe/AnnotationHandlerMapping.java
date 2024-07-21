package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackages;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackages) {
        this.basePackages = basePackages;
        this.handlerExecutions = new HashMap<>();
    }

    @Override
    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");

        ControllerScanner controllerScanner = ControllerScanner.from(basePackages);
        Map<Class<?>, Object> controllersMap = controllerScanner.scan();

        for (Class<?> controllerClass : controllersMap.keySet()) {
            String uriPrefix = extractUriPrefix(controllerClass);
            List<HandlerExecution> handlerExecutions = createHandlerExecutions(controllerClass, controllersMap);
            registerHandlerExecutions(uriPrefix, handlerExecutions);
        }
    }

    private String extractUriPrefix(Class<?> controllerClass) {
        RequestMapping controllerRequestMapping = controllerClass.getAnnotation(RequestMapping.class);
        if (controllerRequestMapping == null) {
            return "";
        }
        return controllerRequestMapping.value();
    }

    private List<HandlerExecution> createHandlerExecutions(Class<?> controllerClass, Map<Class<?>, Object> controllersMap) {
        Set<Method> requestMappingMethods = ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(RequestMapping.class));
        return requestMappingMethods.stream()
                .map(method -> new HandlerExecution(controllersMap.get(controllerClass), method))
                .toList();
    }

    private void registerHandlerExecutions(String uriPrefix, List<HandlerExecution> handlerExecutions) {
        handlerExecutions.forEach(handlerExecution -> {
            HandlerKeys handlerKeys = HandlerKeys.of(uriPrefix, handlerExecution.extractRequestMappingAnnotation());
            handlerKeys.forEach(handlerKey -> this.handlerExecutions.put(handlerKey, handlerExecution));
        });
    }

    /**
     * @return 요청에 해당하는 Handler가 없을 경우 null을 반환
     */
    @Override
    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
