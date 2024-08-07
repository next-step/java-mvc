package com.interface21.web;

public class MethodNotSupportedException extends IllegalStateException {
    public MethodNotSupportedException(){
        super("Method Not allowed");
    }
}
