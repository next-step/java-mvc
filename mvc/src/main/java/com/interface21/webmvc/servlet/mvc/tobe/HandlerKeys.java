package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class HandlerKeys implements Iterable<HandlerKey> {
    private final List<HandlerKey> values;

    private HandlerKeys(List<HandlerKey> values) {
        this.values = values;
    }

    public static HandlerKeys of(String uriPrefix, RequestMapping requestMapping) {
        String uri = uriPrefix + requestMapping.value();
        RequestMethod[] requestMethods = requestMapping.method();
        if (requestMethods == null || requestMethods.length == 0) {
            return createHandlerKeys(RequestMethod.values(), uri);
        }

        return createHandlerKeys(requestMethods, uri);
    }

    private static HandlerKeys createHandlerKeys(RequestMethod[] requestMethods, String uri) {
        List<HandlerKey> handlerKeys = Arrays.stream(requestMethods)
                .map(requestMethod -> new HandlerKey(uri, requestMethod))
                .toList();
        return new HandlerKeys(handlerKeys);
    }

    @Override
    public Iterator<HandlerKey> iterator() {
        return values.iterator();
    }
}
