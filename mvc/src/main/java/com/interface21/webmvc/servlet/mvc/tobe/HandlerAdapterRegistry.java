package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.List;

public class HandlerAdapterRegistry {
    private final List<HandlerAdapter> handlerAdapters;

    public HandlerAdapterRegistry(List<HandlerAdapter> handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }

    void addHandlerAdapter(HandlerAdapter adapter){

    }

    HandlerAdapter getHandlerAdapter(Object instanceMethod){
        return null;
    }
}
