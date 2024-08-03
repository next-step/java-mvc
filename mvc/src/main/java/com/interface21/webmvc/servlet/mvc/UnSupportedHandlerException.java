package com.interface21.webmvc.servlet.mvc;

public class UnSupportedHandlerException extends IllegalArgumentException {
    public UnSupportedHandlerException(String message) {
        super(message);
    }
}
