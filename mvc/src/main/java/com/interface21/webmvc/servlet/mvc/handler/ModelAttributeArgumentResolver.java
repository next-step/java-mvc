package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.ModelAttribute;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.support.HttpRequestBodyParser;
import com.interface21.webmvc.servlet.mvc.support.QueryStringParser;
import com.interface21.webmvc.servlet.mvc.support.TypeConversionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

public class ModelAttributeArgumentResolver implements ArgumentResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.isAnnotationPresent(ModelAttribute.class);
    }

    @Override
    public Object resolve(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String value;
        if (request.getMethod().equals(RequestMethod.POST.name())) {
            HttpRequestBodyParser httpRequestBodyParser = new HttpRequestBodyParser();
            value = httpRequestBodyParser.parse(request);
        } else {
            value = request.getQueryString();
        }
        QueryStringParser queryStringParser = new QueryStringParser();
        Map<String, String> parsedQueryString = queryStringParser.parse(value);

        Class<?> type = parameter.getType();
        Constructor<?>[] declaredConstructors = type.getDeclaredConstructors();
        Constructor<?> declaredConstructor = declaredConstructors[0];
        Parameter[] constructorParameters = declaredConstructor.getParameters();
        Object[] objects = Arrays.stream(constructorParameters)
                .map(param -> {
                    Class<?> type1 = param.getType();
                    if (type1.isPrimitive() && type1.equals(boolean.class)) {
                        return false;
                    }
                    if (type1.isPrimitive()) {
                        return 0;
                    }
                    return null;
                })
                .toArray();
        Object instance = declaredConstructor.newInstance(objects);
        parsedQueryString.keySet()
                .forEach(key -> {
                    try {
                        Field field = type.getDeclaredField(key);
                        field.setAccessible(true);
                        field.set(instance, TypeConversionUtil.convertStringToTargetType(parsedQueryString.get(key), field.getType()));
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException("%s에 해당하는 객체 필드명이 없습니다.".formatted(key), e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        return instance;
    }
}
