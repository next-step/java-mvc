package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.tobe.method.ArgumentResolvers;
import com.interface21.webmvc.servlet.mvc.tobe.method.MethodParameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ArgumentBindInterceptor implements HandlerInterceptor {

    private ArgumentResolvers argumentResolvers = ArgumentResolvers.getInstance();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerExecution handlerExecution = (HandlerExecution) handler;
        resolveHandlerArguments(handlerExecution, request, response);
        return true;

    }

    private void resolveHandlerArguments(HandlerExecution handlerExecution, HttpServletRequest request, HttpServletResponse response) {
        convertToMethodParameters(handlerExecution.getMethod())
            .stream()
            .map(parameter -> argumentResolvers.resolveArgument(parameter, request, response))
            .forEach(handlerExecution::addArgument);
    }

    private List<MethodParameter> convertToMethodParameters(Method method) {
        return Arrays.stream(method.getParameters())
            .map(parameter -> new MethodParameter(method, parameter))
            .toList();
    }
}
