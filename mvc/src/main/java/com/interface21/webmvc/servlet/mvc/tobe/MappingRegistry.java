package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class MappingRegistry {
    private final Map<RequestMappingInfo, HandlerExecution> handlerExecutions = new HashMap<>();

    public void register(final RequestMappingInfo mapping, final Object handler, final Method method) {
        handlerExecutions.put(mapping, new HandlerExecution(handler, method));
    }

    public HandlerExecution getMethod(final String url, final RequestMethod requestMethod) {
        for (RequestMappingInfo mapping : handlerExecutions.keySet()) {
            final boolean match = mapping.isMatch(url, requestMethod);

            if (match) {
                return this.handlerExecutions.get(mapping);
            }
        }

        return null;
    }

}
