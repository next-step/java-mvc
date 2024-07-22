package com.interface21.webmvc.servlet.mvc.tobe.exception;

public class ControllerScanningException extends RuntimeException {
    public ControllerScanningException(String message, Throwable e) {
        super(message, e);
    }
}
