package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        final Reflections reflections = new Reflections(basePackage);
        final Set<Class<?>> handlerClasses = reflections.getTypesAnnotatedWith(Controller.class);

        Map<HandlerKey, HandlerExecution> map = handlerClasses.stream()
                        .map(controller -> {
                            try {
                                return controllerToHandlerExecutions(controller);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }).flatMap(map1 -> map1.entrySet().stream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        handlerExecutions.putAll(map);
        log.info("Initialized AnnotationHandlerMapping!");
    }

    private Map<HandlerKey, HandlerExecution> controllerToHandlerExecutions(final Class<?> controller) throws Exception {
        Object instance = controller.getDeclaredConstructor()
                .newInstance();

        return Arrays.stream(controller.getMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .map(method -> {
                    final RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
                    final String path = requestMapping.value();
                    final RequestMethod[] methodTypes = requestMapping.method();
                    final HandlerExecution handlerExecution;
                    try {
                        handlerExecution = new HandlerExecution(instance, method.getName());
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }

                    return Arrays.stream(methodTypes)
                            .map(methodType -> new HandlerKey(path, methodType))
                            .collect(Collectors.toMap(
                                    handlerKey -> handlerKey,
                                    handlerKey -> handlerExecution
                                    )
                            );
                }).flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Object getHandler(final HttpServletRequest request) {
        final HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
