package com.interface21.webmvc.servlet.mvc.tobe.exception;

public class ControllerInitializationException extends IllegalStateException{

    public ControllerInitializationException() {
        super("Controller Does not have a constructor");
    }
}
