package com.interface21.webmvc.servlet.mvc.resolver;

import com.interface21.webmvc.servlet.mvc.model.MethodParameter;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {

  private final List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
  private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache = new ConcurrentHashMap<>(256);

  public HandlerMethodArgumentResolverComposite addResolver(HandlerMethodArgumentResolver resolver) {
    argumentResolvers.add(resolver);

    return this;
  }

  public void addResolvers(List<? extends HandlerMethodArgumentResolver> resolvers) {
    if (resolvers != null) {
      this.argumentResolvers.addAll(resolvers);
    }
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return this.getArgumentResolver(parameter) != null;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, String urlPattern, HttpServletRequest request) {
    HandlerMethodArgumentResolver resolver = this.getArgumentResolver(parameter);
    if (resolver == null) {
      throw new IllegalArgumentException("Unsupported parameter type [" + parameter.type().getName() + "]. supportsParameter should be called first.");
    }

    return resolver.resolveArgument(parameter, urlPattern, request);
  }

  private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter parameter) {
    HandlerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
    if (result != null) {
      return result;
    }

    for (HandlerMethodArgumentResolver resolver : argumentResolvers) {
      if (resolver.supportsParameter(parameter)) {
        result = resolver;
        this.argumentResolverCache.put(parameter, result);
        break;
      }
    }

    return result;
  }
}
