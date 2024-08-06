package com.interface21.web.bind.annotation;

import com.interface21.web.MethodNotSupprotedException;
import java.util.Arrays;

public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    public static RequestMethod from(String method){
        return Arrays.stream(values())
            .filter(value -> value.name().equals(method))
            .findAny()
            .orElseThrow(MethodNotSupprotedException::new);
    }
}
