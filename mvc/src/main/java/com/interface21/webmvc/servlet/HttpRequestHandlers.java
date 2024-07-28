package com.interface21.webmvc.servlet;

import com.interface21.webmvc.servlet.exception.HttpRequestMethodNotSupportedException;
import com.interface21.webmvc.servlet.exception.NoHandlerFoundException;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestHandlers extends Handlers<HandlerKey> {

    public HandlerExecution getHandler(final HttpServletRequest request) {
        var handlerKeys = handlers.keySet().stream().filter(key -> key.matchUrl(request)).toList();
        if (handlerKeys.isEmpty()) {
            throw new NoHandlerFoundException();
        }
        var handlerKey = handlerKeys.stream().filter(key -> key.matchMethod(request)).findFirst().orElse(null);
        if (handlerKey == null) {
            throw new HttpRequestMethodNotSupportedException();
        }

        return handlers.get(handlerKey);
    }
}
