package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandlerKeys {
    private final List<HandlerKey> handlerKeys = new ArrayList<>();

    public void addMethods(final String url, final RequestMethod[] requestMethods) {
        final List<HandlerKey> keyList = Arrays.stream(requestMethods)
                .map(method -> new HandlerKey(url, method))
                .toList();

        this.handlerKeys.addAll(keyList);
    }

    public boolean hasMatchingMethod(final RequestMethod method) {
        if (handlerKeys.isEmpty()) {
            return true;
        }

        return this.handlerKeys.stream().anyMatch(key -> key.isMatchMethod(method));
    }
}
