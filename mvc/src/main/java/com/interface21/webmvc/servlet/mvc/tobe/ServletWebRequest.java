package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class ServletWebRequest {
    private final ServletRequest request;
    private final ServletResponse response;

    public ServletWebRequest(final ServletRequest request, final ServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public ServletRequest getRequest() {
        return this.request;
    }

    public ServletResponse getResponse() {
        return this.response;
    }
}
