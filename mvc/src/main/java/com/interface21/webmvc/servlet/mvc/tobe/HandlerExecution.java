package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Method;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.interface21.webmvc.servlet.ModelAndView;

public class HandlerExecution {

    private final Object callerClass;
    private final Method method;
    private final MethodParameters args;

    public HandlerExecution(Object clazz, Method method) {
        this.callerClass = clazz;
        this.method = method;
        this.args = MethodParameters.from(method);
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        Object[] args =
                MethodArgumentResolverAdapter.resolveArguments(
                        this.args, new ServletRequestResponse(request, response));

        return (ModelAndView) method.invoke(callerClass, args);
    }
}
