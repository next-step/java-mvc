package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import com.interface21.web.bind.annotation.RequestMethod;

public class HandlerKey {

    public static Function<String, Function<RequestMethod, HandlerKey>> CREATOR =
            url -> requestMethod -> new HandlerKey(url, requestMethod);

    private final String url;
    private final RequestMethod requestMethod;

    public HandlerKey(final String url, final RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public static HandlerKey of(Map.Entry<String, RequestMethod> entry) {
        return HandlerKey.CREATOR.apply(entry.getKey()).apply(entry.getValue());
    }

    @Override
    public String toString() {
        return "HandlerKey{" + "url='" + url + '\'' + ", requestMethod=" + requestMethod + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HandlerKey)) return false;
        HandlerKey that = (HandlerKey) o;
        return Objects.equals(url, that.url) && requestMethod == that.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, requestMethod);
    }
}
