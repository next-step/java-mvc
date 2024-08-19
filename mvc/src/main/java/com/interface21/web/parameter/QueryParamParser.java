package com.interface21.web.parameter;


import com.interface21.web.support.TypedParsers;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class QueryParamParser implements ParameterParser {

    @Override
    public Object parse(Method method, Parameter parameter, HttpServletRequest request) {
        var param = request.getParameter(parameter.getName());
        if (param == null) {
            return null;
        }
        return TypedParsers.parse(parameter.getType(), param);
    }

}
