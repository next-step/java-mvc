package com.interface21.webmvc.servlet.mvc.handler.parameter;


import com.interface21.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class HttpServletResponseResolver implements ArgumentResolver {

    @Override
    public boolean supportsParameter(Parameter parameter) {
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return false;
        }
        return parameter.getType().isAssignableFrom(HttpServletResponse.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return response;
    }
}
