package com.interface21.webmvc.servlet.mvc;

public class HandlerNotFoundException extends RuntimeException {
    public HandlerNotFoundException(String requestUri) {
        super("요청을 지원하는 Handler가 없음. requestUri=" + requestUri);
    }
}
