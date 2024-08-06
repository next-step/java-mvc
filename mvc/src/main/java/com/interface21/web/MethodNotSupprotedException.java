package com.interface21.web;

public class MethodNotSupprotedException extends IllegalStateException {
    public MethodNotSupprotedException(){
        super("Method Not allowed");
    }
}
