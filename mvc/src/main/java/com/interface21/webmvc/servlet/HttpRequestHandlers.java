package com.interface21.webmvc.servlet;

import com.interface21.webmvc.servlet.exception.HttpRequestMethodNotSupportedException;
import com.interface21.webmvc.servlet.exception.NoHandlerFoundException;
import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestHandlers extends Handlers<HandlerKey> {

    @Override
    public HandlerExecution getHandler(final HttpServletRequest request) {
        var handlerKey = handlers.keySet().stream().filter(key -> key.matchUrl(request)).findFirst().orElse(null);
        if (handlerKey == null) {
            throw new NoHandlerFoundException();
        }
        if (!handlerKey.matchMethod(request)) {
            throw new HttpRequestMethodNotSupportedException();
        }

        return handlers.get(handlerKey);
    }
}
