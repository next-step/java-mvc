package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

public class AnnotationHandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackages;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(String... basePackages) {
        this.basePackages = basePackages;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        log.info("Initializing AnnotationHandlerMapping");

        ControllerScanner controllerScanner = ControllerScanner.of(basePackages);
        Map<Class<?>,Object> controllerMap = controllerScanner.scan();

        // 스캔된 각 컨트롤러 클래스에 대해 인스턴스를 생성하고 핸들러를 매핑
        //Set<Class<?>> controllerClasses = scanControllers();
        for (Class<?> controllerClass : controllerMap.keySet()) {
            //싱글톤 패턴 구현 & 의존성 주입 준비
            Object controllerInstance = createControllerInstance(controllerClass);

            //mapHandlers는 컨트롤러 클래스의 메소드들을 분석하여 HTTP 요청 패턴(URL과 HTTP 메소드)을 메소드에 매핑하는 역할
            mapHandlers(controllerClass, controllerInstance);
        }
        log.info("AnnotationHandlerMapping initialized {}", handlerExecutions.size());
    }

    public Object getHandler(HttpServletRequest request) {
        // 요청 URI와 HTTP 메소드를 기반으로 HandlerKey를 생성
        HandlerKey key = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(key);
    }

    private Object createControllerInstance(Class<?> controllerClass) {
        try {
            return controllerClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to load instance", e);
        }
    }

    private void mapHandlers(Class<?> controllerClass, Object controllerInstance) {
        String baseUrl = extractBaseUrl(controllerClass);
        for (Method method : controllerClass.getMethods()) {
            RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
            if (methodMapping != null) {
                String fullUrl = baseUrl + methodMapping.value();
                RequestMethod[] requestMethods = methodMapping.method();
                if (requestMethods.length == 0) {
                    requestMethods = RequestMethod.values();
                }
                for (RequestMethod requestMethod : requestMethods) {
                    HandlerKey key = new HandlerKey(fullUrl, requestMethod);
                    HandlerExecution execution = new HandlerExecution(controllerInstance, method);
                    handlerExecutions.put(key, execution);
                }
            }
        }
    }

    private String extractBaseUrl(Class<?> controllerClass) {
        RequestMapping classMapping = controllerClass.getAnnotation(RequestMapping.class);
        return (classMapping != null) ? classMapping.value() : "";
    }
}