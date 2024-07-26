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
        String name = getName(parameter, requestParam);
        String value = parsedQueryString.get(name);

        if (requestParam.required() && value == null) {
            throw new IllegalArgumentException("필수 값이 누락되었습니다. name=" + name);
        }

        return TypeConversionUtil.convertStringToTargetType(value, parameter.getType());
    }

    private String getName(Parameter parameter, RequestParam requestParam) {
        if (requestParam.name().isBlank()) {
            return parameter.getName();
        }
        return requestParam.name();
    }
}
