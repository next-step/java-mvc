package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MethodParameterParser {

    boolean accept();

    Object parseValue(HttpServletRequest request, HttpServletResponse response);
}
