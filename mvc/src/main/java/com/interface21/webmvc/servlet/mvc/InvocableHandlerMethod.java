package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.MethodArgumentResolutionException;
import com.interface21.webmvc.servlet.mvc.model.HandlerMethod;
import com.interface21.webmvc.servlet.mvc.model.MethodParameter;
import com.interface21.webmvc.servlet.mvc.resolver.HandlerMethodArgumentResolverComposite;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import org.apache.commons.lang3.ObjectUtils;

public class InvocableHandlerMethod extends HandlerMethod {
  private static final Object[] EMPTY_ARGS = new Object[0];

  private final HandlerMethodArgumentResolverComposite resolvers;

  public InvocableHandlerMethod(Method method, HandlerMethodArgumentResolverComposite resolvers) {
    super(method);
    this.resolvers = resolvers;
  }

  public Object[] getMethodArgumentValues(HttpServletRequest request, String urlPattern) {
    MethodParameter[] parameters = this.methodParameters();

    Object[] args = new Object[parameters.length];

    if(ObjectUtils.isEmpty(parameters)) {
      return EMPTY_ARGS;
    }

    for(int i = 0; i < parameters.length; i++) {
      if(!resolvers.supportsParameter(parameters[i])) {
        throw new MethodArgumentResolutionException(parameters[i]);
      }

      args[i] = resolvers.resolveArgument(parameters[i], urlPattern, request);
    }

    return args;
  }
}
