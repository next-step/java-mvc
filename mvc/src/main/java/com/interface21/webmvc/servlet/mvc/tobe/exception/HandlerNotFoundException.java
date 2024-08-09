package com.interface21.webmvc.servlet.mvc.tobe.exception;

public class HandlerNotFoundException extends RuntimeException {
    public HandlerNotFoundException(String handler) {
        super("지원하는 핸들러가 없습니다 : " + handler);
    }
}
