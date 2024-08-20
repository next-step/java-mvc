package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.tobe.exception.RequestMethodNotDefinedException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;


public class AnnotationHandlerMapping implements HandlerMapping {

  private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

  private final Object[] basePackages;
  private final Map<HandlerKey, HandlerExecution> handlerExecutionsMap;

  public AnnotationHandlerMapping(String... basePackages) {
    this.basePackages = basePackages;
    this.handlerExecutionsMap = new HashMap<>();
  }

  @Override
  public void initialize() {
    log.info("Initializing AnnotationHandlerMapping");

    ControllerScanner controllerScanner = ControllerScanner.of(basePackages);
    Map<Class<?>, Object> controllerMap = controllerScanner.scan();

    //NOTE: stream API 사용

    // 스캔된 각 컨트롤러 클래스에 대해 인스턴스를 생성하고 핸들러를 매핑
    controllerMap.entrySet().stream().forEach(entry -> {
      //싱글톤 패턴 구현 & 의존성 주입 준비
      Class<?> controllerClass = entry.getKey();
      Object controllerInstance = entry.getValue(); // ControllerScanner에서 생성한 인스턴스 사용
      //mapHandlers는 컨트롤러 클래스의 메소드들을 분석하여 HTTP 요청 패턴(URL과 HTTP 메소드)을 메소드에 매핑하는 역할
      mapHandlers(controllerClass, controllerInstance);
    });
    log.info("AnnotationHandlerMapping initialized {}", handlerExecutionsMap.size());
  }

  @Override
  public Object getHandler(HttpServletRequest request) {
    // 요청 URI와 HTTP 메소드를 기반으로 HandlerKey를 생성
    HandlerKey key = new HandlerKey(request.getRequestURI(),
        RequestMethod.valueOf(request.getMethod()));
    return handlerExecutionsMap.get(key);
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
    if (requestMethods.length == 0) {
      throw new RequestMethodNotDefinedException();
    }
    return requestMethods;
  }

  private void createHandlerExecutions(String fullUrl, RequestMethod[] requestMethods,
      Object controllerInstance, Method method) {
    RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
    HandlerExecution execution = new HandlerExecution(controllerInstance, method,requestMapping.value());

    Arrays.stream(requestMethods)
        .forEach(requestMethod -> {
          HandlerKey key = new HandlerKey(fullUrl, requestMethod);
          handlerExecutionsMap.put(key, execution);
        });
  }

  private String extractBaseUrl(Class<?> controllerClass) {
    RequestMapping classMapping = controllerClass.getAnnotation(RequestMapping.class);
    return (classMapping != null) ? classMapping.value() : "";
  }
}
