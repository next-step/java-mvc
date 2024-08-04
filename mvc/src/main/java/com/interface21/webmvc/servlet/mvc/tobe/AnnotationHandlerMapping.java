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
  private final Map<HandlerKey, HandlerExecution> handlerExecutionsMap;

  public AnnotationHandlerMapping(String... basePackages) {
    this.basePackages = basePackages;
    this.handlerExecutionsMap = new HashMap<>();
  }

  public void initialize() {
    log.info("Initializing AnnotationHandlerMapping");

    ControllerScanner controllerScanner = ControllerScanner.of(basePackages);
    Map<Class<?>, Object> controllerMap = controllerScanner.scan();

    //NOTE: stream API 사용

    // 스캔된 각 컨트롤러 클래스에 대해 인스턴스를 생성하고 핸들러를 매핑
    controllerMap.entrySet().stream().forEach(entry -> {
      Class<?> controllerClass = entry.getKey();
      //싱글톤 패턴 구현 & 의존성 주입 준비
      Object controllerInstance = createControllerInstance(controllerClass);

      //mapHandlers는 컨트롤러 클래스의 메소드들을 분석하여 HTTP 요청 패턴(URL과 HTTP 메소드)을 메소드에 매핑하는 역할
      mapHandlers(controllerClass, controllerInstance);
    });
    log.info("AnnotationHandlerMapping initialized {}", handlerExecutionsMap.size());
  }

  public Object getHandler(HttpServletRequest request) {
    // 요청 URI와 HTTP 메소드를 기반으로 HandlerKey를 생성
    HandlerKey key = new HandlerKey(request.getRequestURI(),
        RequestMethod.valueOf(request.getMethod()));
    return handlerExecutionsMap.get(key);
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
    Arrays.stream(controllerClass.getMethods())
        .filter(this::hasRequestMapping)
        .forEach(method -> processMethodMapping(method, controllerInstance, baseUrl));
  }

  private boolean hasRequestMapping(Method method) {
    return method.isAnnotationPresent(RequestMapping.class);
  }

  private void processMethodMapping(Method method, Object controllerInstance, String baseUrl) {
    RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
    String fullUrl = baseUrl + methodMapping.value();
    RequestMethod[] requestMethods = getRequestMethods(methodMapping);

    createHandlerExecutions(fullUrl, requestMethods, controllerInstance, method);
  }

  private RequestMethod[] getRequestMethods(RequestMapping methodMapping) {
    RequestMethod[] requestMethods = methodMapping.method();
    return requestMethods.length == 0 ? RequestMethod.values() : requestMethods;
  }

  private void createHandlerExecutions(String fullUrl, RequestMethod[] requestMethods,
      Object controllerInstance, Method method) {
    Arrays.stream(requestMethods)
        .forEach(requestMethod -> {
          HandlerKey key = new HandlerKey(fullUrl, requestMethod);
          HandlerExecution execution = new HandlerExecution(controllerInstance, method);
          handlerExecutionsMap.put(key, execution);
        });
  }

  private String extractBaseUrl(Class<?> controllerClass) {
    RequestMapping classMapping = controllerClass.getAnnotation(RequestMapping.class);
    return (classMapping != null) ? classMapping.value() : "";
  }
}