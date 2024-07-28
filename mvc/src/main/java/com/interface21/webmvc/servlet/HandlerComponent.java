package com.interface21.webmvc.servlet;

import java.lang.reflect.Method;

public class HandlerComponent {
    protected final Object instance;
    protected final Method[] methods;

    public HandlerComponent(Class<?> type, Object instance) {
        this.instance = instance;
        methods = type.getDeclaredMethods();
    }
}
