package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

import static com.interface21.webmvc.servlet.mvc.tobe.support.ParameterConverter.convertToMethodArg;

public class HandlerExecution {
    private Method method;
    private Object object;

    public HandlerExecution(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        if (isParameterMappingMethod()) {
            return (ModelAndView) method.invoke(object, convertToMethodArg(method.getParameters(), request.getParameterMap()));
        }
        return (ModelAndView) method.invoke(object, request, response);
    }

    private boolean isParameterMappingMethod() {
        Parameter[] parameters = method.getParameters();
        if (parameters.length != 2) {
            return false;
        }

        if (!parameters[0].getType().equals(HttpServletRequest.class)) {
            return false;
        }

        return parameters[0].getType().equals(HttpServletResponse.class);
    }
}
