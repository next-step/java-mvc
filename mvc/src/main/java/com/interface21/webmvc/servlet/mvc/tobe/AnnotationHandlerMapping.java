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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
        log.info("Initialized AnnotationHandlerMapping!");
        handlerExecutions.putAll(mappingBasePackageHandlers());
    }

    private Map<HandlerKey, HandlerExecution> mappingBasePackageHandlers() {
        return Arrays.stream(basePackage)
                .map(this::mappingHandler)
                .flatMap(handlers -> handlers.entrySet().stream())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private Map<HandlerKey, HandlerExecution> mappingHandler(Object basePackage) {
        return new Reflections(basePackage).getTypesAnnotatedWith(Controller.class)
                .stream()
                .map(this::parseControllerHandler)
                .flatMap(handlers -> handlers.entrySet().stream())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private Map<HandlerKey, HandlerExecution> parseControllerHandler(Class<?> controller) {
        Object controllerInstance = createNoArgInstance(controller);

        return Arrays.stream(controller.getMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .map(method -> parseMethod(method, controllerInstance))
                .flatMap(handlers -> handlers.entrySet().stream())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private Object createNoArgInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor()
                    .newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("클래스가 아닌 추상클래스, 인터페이스는 생성자를 만들 수 없습니다.");
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("클래스의 생성자에 접근할 수 없습니다.");
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("생성자에서 예외가 발생했습니다.", e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("지원하는 생성자가 존재하지 않습니다.");
        }
    }

    private Map<HandlerKey, HandlerExecution> parseMethod(Method method, Object controllerInstance) {
        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
        String url = requestMapping.value();
        HandlerExecution handlerExecution = new HandlerExecution(controllerInstance, method.getName());

        return Arrays.stream(requestMapping.method())
                .map(requestMethod -> new HandlerKey(url, requestMethod))
                .collect(Collectors.toMap(
                        handlerKey -> handlerKey,
                        handlerKey -> handlerExecution)
                );
    }

    public Object getHandler(final HttpServletRequest request) {
        HandlerKey handlerKey = createHandlerKey(request);
        if (handlerExecutions.containsKey(handlerKey)) {
            return handlerExecutions.get(handlerKey);
        }
        throw new IllegalArgumentException("지원하지 않는 request 요청입니다.");
    }

    private HandlerKey createHandlerKey(HttpServletRequest request) {
        return new HandlerKey(
                request.getRequestURI(),
                RequestMethod.valueOf(request.getMethod())
        );
    }
}
