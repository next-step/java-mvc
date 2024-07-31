package com.interface21.webmvc.servlet.mvc.handler.parameter;

import com.interface21.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class BasicArgumentTypeResolver implements ArgumentResolver {

    @Override
    public boolean supportsParameter(Parameter parameter) {
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return false;
        }
        return BasicArgumentType.isBasicType(parameter.getType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Parameter parameter = methodParameter.getParameter();
        String parameterName = parameter.getName();
        String parameterValue = request.getParameter(parameterName);

        return BasicArgumentType.convert(parameterValue, parameter.getType());
    }
}
