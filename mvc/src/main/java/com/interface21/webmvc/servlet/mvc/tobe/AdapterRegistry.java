package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.Optional;

public interface AdapterRegistry {
    Optional<HandlerAdapter> getHandlerAdapter(Object handler);
}
