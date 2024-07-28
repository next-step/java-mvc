package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HandlerMappingRegistry {

    private final List<HandlerMapping> handlerMappings = new ArrayList<>();

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappings.add(handlerMapping);
    }

    public Object getHandler(HttpServletRequest httpServletRequest) {
        return handlerMappings.stream()
            .map(handlerMapping -> handlerMapping.getHandler(httpServletRequest))
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(httpServletRequest.getRequestURI() + " 핸들러는 지원되지 않습니다."));
    }
}
