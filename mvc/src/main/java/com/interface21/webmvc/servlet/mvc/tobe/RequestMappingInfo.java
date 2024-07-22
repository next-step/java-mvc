package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Objects;

public final class RequestMappingInfo {

    private final String url;
    private final HandlerKey[] handlerKeys;

    public RequestMappingInfo(final String url, final RequestMethod... requestMethods) {
        this.url = url;
        this.handlerKeys = Arrays.stream(requestMethods)
                .map(method -> new HandlerKey(url, method))
                .toArray(HandlerKey[]::new);
    }

    public boolean isMatch(final String url, final RequestMethod method) {
        return (url.equals(this.url)) && isMethodMatch(method);
    }

    private boolean isMethodMatch(final RequestMethod method) {
        return Arrays.stream(handlerKeys).anyMatch(key -> Objects.equals(key, new HandlerKey(url, method)));
    }

}
