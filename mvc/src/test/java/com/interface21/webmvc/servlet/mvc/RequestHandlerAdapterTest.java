package com.interface21.webmvc.servlet.mvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import samples.TestUserController;

class RequestHandlerAdapterTest {

  private final RequestHandlerAdapter handlerAdapter = new RequestHandlerAdapter();

  @Test
  void ssdfsdf() throws Exception {
    handlerAdapter.addArgumentResolvers(handlerAdapter.getDefaultArgumentResolvers());

    final var request = mock(HttpServletRequest.class);
    final var response = mock(HttpServletResponse.class);
    AnnotationHandlerMapping handlerMapping = new AnnotationHandlerMapping();

    final var handlerKey = new HandlerKey("/users/{id}", RequestMethod.GET);
    Class<TestUserController> clazz = TestUserController.class;
    Method[] methods = clazz.getDeclaredMethods();

    Method method2 = null;
    for(Method method : methods) {
      if(method.getName().equals("show_pathvariable")) {
        method2 = method;
      }
    }
    HandlerExecution handlerExecution = new HandlerExecution(new AnnotationControllerClass(clazz), method2);

    handlerAdapter.handle(request, response, handlerExecution);
  }
}