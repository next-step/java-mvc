package com.interface21.webmvc.servlet.mvc;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class HandlerMappingRegistry {

  private final List<HandlerMapping> handlerMappings = new ArrayList<>();

  public HandlerMappingRegistry() {
  }

  public void initialize() {
    handlerMappings.forEach(HandlerMapping::initialize);
  }

  public void addHandlerMapping(final HandlerMapping handlerMapping) {
      handlerMappings.add(handlerMapping);
  }

  public HandlerExecution getHandler(final HttpServletRequest request) {
    return handlerMappings.stream()
        .filter(handlerMapping -> handlerMapping.getHandler(request) != null)
        .findAny()
        .map(it -> it.getHandler(request))
        .orElseThrow(() -> new UnSupportedHandlerException("Unsupported request: " + request.getRequestURI()));
  }
}
