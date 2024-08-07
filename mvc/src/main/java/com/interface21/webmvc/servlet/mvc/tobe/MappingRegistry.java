package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface MappingRegistry {
     Optional<Object> getHandler(HttpServletRequest request);
}
