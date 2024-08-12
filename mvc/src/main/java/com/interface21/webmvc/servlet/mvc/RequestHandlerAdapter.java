package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.mvc.resolver.HandlerMethodArgumentResolver;
import com.interface21.webmvc.servlet.mvc.resolver.HandlerMethodArgumentResolverComposite;
import com.interface21.webmvc.servlet.mvc.resolver.PathVariableHandlerMethodArgumentResolver;
import com.interface21.webmvc.servlet.mvc.resolver.RequestParamHandlerMethodArgumentResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class RequestHandlerAdapter implements HandlerAdapter {

  private static final RequestHandlerAdapter instance = new RequestHandlerAdapter();
  private HandlerMethodArgumentResolverComposite argumentResolvers;

  private RequestHandlerAdapter() {
  }

  // 미리 생성된 인스턴스를 반환
  public static RequestHandlerAdapter getInstance() {
    return instance;
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, HandlerExecution handler) throws Exception {
    final InvocableHandlerMethod invocableHandlerMethod = new InvocableHandlerMethod(handler.method(), argumentResolvers);
    final Object[] arguments = invocableHandlerMethod.getMethodArgumentValues(request, handler.urlPattern());
    final var modelAndView = handler.handle(arguments);

    modelAndView.getView().render(modelAndView.getModel(), request, response);
  }

  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    if (argumentResolvers == null) {
      this.argumentResolvers = null;
      return;
    }

    this.argumentResolvers = new HandlerMethodArgumentResolverComposite();
    this.argumentResolvers.addResolvers(argumentResolvers);
  }

  public List<HandlerMethodArgumentResolver> getDefaultArgumentResolvers() {
    List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>(30);

    // Annotation-based argument resolution
    resolvers.add(new PathVariableHandlerMethodArgumentResolver());
    resolvers.add(new RequestParamHandlerMethodArgumentResolver());
    return resolvers;
  }
}
