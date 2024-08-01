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

    public String getUrl() {
        return url;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
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

    public boolean matches(final HandlerKey handlerKey) {
        if (this.equals(handlerKey)) {
            return true;
        }

        return isUrlAndMethodMatching(handlerKey);
    }

    private boolean isUrlAndMethodMatching(final HandlerKey handlerKey) {
        boolean urlMatches = PathPatternUtil.isUrlMatch(this.url, handlerKey.url);
        boolean methodMatches = this.requestMethod.equals(handlerKey.requestMethod);
        return urlMatches && methodMatches;
    }
}
