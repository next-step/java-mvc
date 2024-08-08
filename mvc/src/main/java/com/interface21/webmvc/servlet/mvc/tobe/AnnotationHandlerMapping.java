package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.tobe.parameterresolver.HttpServletRequestResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameterresolver.HttpServletResponseResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameterresolver.HttpSessionResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameterresolver.PathVariableParameterResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameterresolver.RequestParameterResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameterresolver.StructuredRequestParameterParameterResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.Store;
import org.reflections.util.QueryFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
    private final List<ParameterResolver> parameterResolvers;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
        parameterResolvers = new ArrayList<>();
    }

    public void initialize() {
        registerRequestMapping();
        registerParameterResolvers();

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

    private void registerParameterResolvers() {
        parameterResolvers.add(new HttpServletRequestResolver());
        parameterResolvers.add(new HttpServletResponseResolver());
        parameterResolvers.add(new HttpSessionResolver());
        parameterResolvers.add(new RequestParameterResolver());
        parameterResolvers.add(new StructuredRequestParameterParameterResolver());
        parameterResolvers.add(new PathVariableParameterResolver());
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

            if (handlerExecutions.containsKey(handlerKey)) {
                throw new IllegalStateException("이미 동일한 매핑이 등록되어 있습니다.");
            }

            handlerExecutions.put(handlerKey, newHandlerExecution(controllerObject, methodType, handlerKey));
        }
    }

    private HandlerExecution newHandlerExecution(Object controllerObject, Method methodType, HandlerKey handlerKey) {
        return new HandlerExecution(controllerObject, methodType, handlerKey, parameterResolvers);
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
