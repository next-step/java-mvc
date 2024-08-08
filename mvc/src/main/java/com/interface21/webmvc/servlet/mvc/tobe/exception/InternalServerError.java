package com.interface21.webmvc.servlet.mvc.tobe.exception;

public class InternalServerError extends RuntimeException {
    public InternalServerError(Throwable exception) {
        super("핸들러 동작 중에 예외가 발생했습니다.", exception);
    }
}
