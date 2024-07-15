package com.interface21.webmvc.servlet.mvc.exception;

public class ControllerInitializeException extends RuntimeException {
    public ControllerInitializeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
