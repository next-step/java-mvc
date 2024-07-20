package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

import static com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil.isUrlMatch;

public class HandlerKey {

    private final String url;
    private final RequestMethod requestMethod;

    public HandlerKey(final String url, final RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public static HandlerKey from(final HttpServletRequest request) {
        return new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
    }

    public boolean isMatchUrl(HandlerKey handlerKey) {
        return this.url.equals(handlerKey.url) || isUrlMatch(this.url, handlerKey.url);
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
