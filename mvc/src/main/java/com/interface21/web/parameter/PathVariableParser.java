package com.interface21.web.parameter;


import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.support.PathPatternUtil;
import com.interface21.web.support.TypedParsers;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class PathVariableParser implements ParameterParser {

    @Override
    public Object parse(Method method, Parameter parameter, HttpServletRequest request) {
        var variable = parameter.getAnnotation(PathVariable.class);
        var requestMapping = method.getAnnotation(RequestMapping.class);
        if (variable == null || requestMapping == null) {
            return null;
        }
        var name = variable.name();
        if (name.isBlank()) {
            name = parameter.getName();
        }
        var param = PathPatternUtil.getUriValue(requestMapping.value(), request.getRequestURI(), name);
        if (param == null) {
            return null;
        }
        return TypedParsers.parse(parameter.getType(), param);
    }

}
