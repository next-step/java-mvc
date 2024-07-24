package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

class MappingRegistry {
    private final Map<RequestMappingInfo, HandlerExecution> handlerExecutions = new HashMap<>();

    public void registerControllers(final List<Object> controllers) {
        controllers.forEach(this::detectHandlerMethods);
    }

    private void detectHandlerMethods(final Object handler) {
        Class<?> clazz = handler.getClass();
        Method[] handlerMethods = clazz.getDeclaredMethods();

        StringBuilder parentPath = extractClassLevelUrlPath(clazz);

        final String basePath = parentPath.toString();
        final Map<Method, RequestMappingInfo> methods = extractMethodMappings(handlerMethods, basePath);

        methods.forEach((method, mapping) -> register(mapping, handler, method));
    }

    private StringBuilder extractClassLevelUrlPath(final Class<?> clazz) {
        StringBuilder parentPath = new StringBuilder();

        final Controller controller = clazz.getAnnotation(Controller.class);
        parentPath.append(controller.path());
        return parentPath;
    }

    private Map<Method, RequestMappingInfo> extractMethodMappings(final Method[] handlerMethods, final String basePath) {
        return Arrays.stream(handlerMethods)
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .collect(Collectors.toMap(
                method -> method,
                method -> extractRequestMappingInfo(basePath, method),
                (existing, replacement) -> existing,
                LinkedHashMap::new
        ));
    }

    private RequestMappingInfo extractRequestMappingInfo(final String basePath, final Method method) {
        StringBuilder path = new StringBuilder(basePath);

        final RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
        path.append(requestMapping.value());

        return new RequestMappingInfo(path.toString(), requestMapping.method());
    }

    private void register(final RequestMappingInfo mapping, final Object handler, final Method method) {
        handlerExecutions.put(mapping, new HandlerExecution(handler, method));
    }

    public HandlerExecution getMethod(final String url, final RequestMethod requestMethod) {
        return handlerExecutions.keySet().stream()
                .filter(mapping -> mapping.isMatch(url, requestMethod))
                .findFirst()
                .map(mapping -> handlerExecutions.get(mapping))
                .orElse(null);
    }

}
