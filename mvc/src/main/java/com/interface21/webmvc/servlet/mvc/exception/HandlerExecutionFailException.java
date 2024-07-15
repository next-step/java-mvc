package com.interface21.webmvc.servlet.mvc.exception;

public class HandlerExecutionFailException extends RuntimeException {
    public HandlerExecutionFailException(final String message) {
        super(message);
    }
}
