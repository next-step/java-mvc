package com.interface21.webmvc.servlet.mvc;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    Optional<HandlerMapping> handlerMappingOptional = handlerMappings.stream()
        .filter(handlerMapping -> handlerMapping.getHandler(request) != null)
        .findFirst();
    if(handlerMappingOptional.isEmpty()) {
      throw new UnSupportedHandlerException("Unsupported request: " + request.getRequestURI());
    }

    HandlerMapping handlerMapping = handlerMappingOptional.get();
    return handlerMapping.getHandler(request);
  }
}
