package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        final BeanScanner beanScanner = new BeanScanner(basePackage);
        final Set<Class<?>> controllers = beanScanner.scanClassesTypeAnnotatedWith(Controller.class);

        controllers.stream().map(this::getNewInstance).forEach(this::detectHandlerMethods);
    }

    private Object getNewInstance(final Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void detectHandlerMethods(final Object handler) {
        Class<?> clazz = handler.getClass();
        Method[] handlerMethods = clazz.getDeclaredMethods();

        StringBuilder parentPath = extractClassLevelUrlPath(clazz);

        final String basePath = parentPath.toString();

        final Map<Method, RequestMappingInfo> methods = extractMethodMappings(handlerMethods, basePath);

        methods.forEach((method, mapping) -> registerHandlerMethod(handler, method, mapping));
    }

    private StringBuilder extractClassLevelUrlPath(final Class<?> clazz) {
        StringBuilder parentPath = new StringBuilder();

        final Controller controller = clazz.getAnnotation(Controller.class);
        parentPath.append(controller.path());
        return parentPath;
    }

    private Map<Method, RequestMappingInfo> extractMethodMappings(final Method[] handlerMethods, final String basePath) {
        return Arrays.stream(handlerMethods).collect(Collectors.toMap(
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

    private void registerHandlerMethod(final Object handler, final Method method, final RequestMappingInfo mapping) {
        this.mappingRegistry.register(mapping, handler, method);
    }

    public Object getHandler(final HttpServletRequest request) {
        return this.mappingRegistry.getMethod(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
    }

    static class MappingRegistry {
        private final Map<RequestMappingInfo, HandlerExecution> handlerExecutions = new HashMap<>();

        public void register(final RequestMappingInfo mapping, final Object handler, final Method method) {
            handlerExecutions.put(mapping, new HandlerExecution(handler, method));
        }

        public HandlerExecution getMethod(final String url, final RequestMethod requestMethod) {
            for (RequestMappingInfo mapping : handlerExecutions.keySet()) {
                final boolean match = mapping.isMatch(url, requestMethod);

                if (match) {
                    return this.handlerExecutions.get(mapping);
                }
            }

            return null;
        }

    }
}
