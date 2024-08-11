package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;
import java.util.Objects;

public class HandlerKey {

    private final String url;
    private final RequestMethod requestMethod;

    public HandlerKey(final String url, final RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public static HandlerKey of(final String url, final RequestMethod requestMethod) {
        return new HandlerKey(url, requestMethod);
    }

    public boolean matches(HandlerKey handlerKey) {
        return PathPatternUtil.isUrlMatch(this.url, handlerKey.url) ||
            this.equals(handlerKey);
    }

    @Override
    public String toString() {
        return "HandlerKey{" +
            "url='" + url + '\'' +
            ", requestMethod=" + requestMethod +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HandlerKey)) {
            return false;
        }
        HandlerKey that = (HandlerKey) o;
        return Objects.equals(url, that.url) && requestMethod == that.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, requestMethod);
    }
}
