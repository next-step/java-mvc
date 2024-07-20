package com.interface21.webmvc.servlet.mvc.tobe;

public class HandlerAdapterNotFoundException extends RuntimeException {
    public HandlerAdapterNotFoundException(String handlerType) {
        super("지원하는 HandlerAdapter가 없습니다. handlerType=" + handlerType);
    }
}
