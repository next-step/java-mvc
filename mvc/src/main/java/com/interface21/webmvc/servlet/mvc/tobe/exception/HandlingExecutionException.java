package com.interface21.webmvc.servlet.mvc.tobe.exception;

public class HandlingExecutionException extends RuntimeException {
    public HandlingExecutionException(Throwable exception) {
        super("핸들러 동작 중에 예외가 발생했습니다.", exception);
    }
}
