package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.RequestParam;
import com.interface21.webmvc.servlet.mvc.support.QueryStringParser;
import com.interface21.webmvc.servlet.mvc.support.TypeConversionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class RequestParamArgumentResolver implements ArgumentResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestParam.class);
    }

    @Override
    public Object resolve(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String queryString = request.getQueryString();
        QueryStringParser queryStringParser = new QueryStringParser();
        Map<String, String> parsedQueryString = queryStringParser.parse(queryString);

        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        String name;
        if (requestParam.name().isBlank()) {
            name = parameter.getName();
        } else {
            name = requestParam.name();
        }
        String value = parsedQueryString.get(name);

        if (requestParam.required() && value == null) {
            throw new IllegalArgumentException("필수 값 누락");
        }

        return TypeConversionUtil.convertStringToTargetType(value, parameter.getType());
    }
}
