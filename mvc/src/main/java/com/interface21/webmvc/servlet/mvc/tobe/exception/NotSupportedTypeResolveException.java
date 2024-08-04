package com.interface21.webmvc.servlet.mvc.tobe.exception;

public class NotSupportedTypeResolveException extends RuntimeException {
    public NotSupportedTypeResolveException(Class<?> type) {
        super("지원하지 않는 타입의 파라메터입니다: " + type.getName());
    }
}
