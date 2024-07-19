package com.interface21.webmvc.servlet.mvc.tobe;

public class ControllerDefaultConstructorNotFoundException extends RuntimeException {
    public ControllerDefaultConstructorNotFoundException(ReflectiveOperationException e) {
        super("Controller의 기본 생성자를 찾을 수 없습니다.", e);
    }
}
