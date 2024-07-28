package com.interface21.webmvc.servlet.mvc.tobe.exception;

public class ViewMissingException extends RuntimeException {
    public ViewMissingException() {
        super("view resolve 에 실패했습니다.");
    }
}
