package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.Store;
import org.reflections.util.QueryFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.reflections.ReflectionUtils.Methods;
import static org.reflections.util.ReflectionUtilsPredicates.withAnnotation;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        registerRequestMapping();

        log.info("Initialized AnnotationHandlerMapping!");
    }

    private void registerRequestMapping() {
        ControllerScanner scanner = new ControllerScanner(new Reflections(basePackage));

        Map<Class<?>, Object> controllersMap = scanner.getControllers();
        List<Method> requestMappingMethods = getRequestMappingMethods(controllersMap.keySet());
        for (Method requestMappedMethod : requestMappingMethods) {
            registerMappings(requestMappedMethod, controllersMap);
        }
    }

    private List<Method> getRequestMappingMethods(Set<Class<?>> controllers) {
        return controllers.stream()
                          .flatMap(controllerType -> getRequestMappings(controllerType).stream())
                          .collect(Collectors.toList());
    }

    private void registerMappings(Method methodType, Map<Class<?>, Object> controllersMap) {
        RequestMapping annotation = methodType.getAnnotation(RequestMapping.class);
        var uri = annotation.value();
        var requestMethods = annotation.method();

        var controllerObject = controllersMap.get(methodType.getDeclaringClass());

        for (RequestMethod requestMethod : requestMethods) {
            HandlerKey handlerKey = new HandlerKey(uri, requestMethod);
            handlerExecutions.put(handlerKey, new HandlerExecution(controllerObject, methodType, handlerKey));
        }
    }

    private Set<Method> getRequestMappings(Class<?> controllerType) {
        QueryFunction<Store, Method> requestMappingQuery =
                Methods.of(controllerType).filter(withAnnotation(RequestMapping.class));
        return ReflectionUtils.get(requestMappingQuery);
    }

    @Override
    public HandlerExecution getHandler(final HttpServletRequest request) {
        return handlerExecutions.keySet().stream()
                                .filter(handlerKey -> handlerKey.isMatch(request.getMethod(), request.getRequestURI()))
                                .map(handlerExecutions::get)
                                .findAny()
                                .orElse(null);
    }
}
