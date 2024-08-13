package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MethodArgumentResolver {

    boolean accept();

    Object parse(HttpServletRequest request, HttpServletResponse response);
}
