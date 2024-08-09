package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.ModelAttribute;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.web.http.MediaType;
import com.interface21.webmvc.servlet.mvc.support.HttpRequestBodyParser;
import com.interface21.webmvc.servlet.mvc.support.QueryStringParser;
import com.interface21.webmvc.servlet.mvc.support.TypeConversionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Map;

public class ModelAttributeArgumentResolver implements ArgumentResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.isAnnotationPresent(ModelAttribute.class);
    }

    @Override
    public Object resolve(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String queryString = getQueryString(request);
        Map<String, String> parsedQueryString = QueryStringParser.parse(queryString);

        Class<?> parameterType = parameter.getType();
        Object parameterTypeInstance = createParameterTypeInstance(parameterType);
        setUpInstanceFieldValue(parsedQueryString, parameterType, parameterTypeInstance);
        return parameterTypeInstance;
    }

    private String getQueryString(HttpServletRequest request) throws IOException {
        if (request.getMethod().equals(RequestMethod.POST.name()) && request.getHeader("Content-Type").equals(MediaType.FORM_URL_ENCODED)) {
            return HttpRequestBodyParser.parse(request);
        }
        return request.getQueryString();
    }

    private Object createParameterTypeInstance(Class<?> parameterType) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> declaredConstructor = parameterType.getDeclaredConstructors()[0];
        Object[] objects = Arrays.stream(declaredConstructor.getParameters())
                .map(this::setUpDefaultValue)
                .toArray();
        return declaredConstructor.newInstance(objects);
    }

    private Object setUpDefaultValue(Parameter param) {
        Class<?> type1 = param.getType();
        if (type1.isPrimitive() && type1.equals(boolean.class)) {
            return false;
        }
        if (type1.isPrimitive()) {
            return 0;
        }
        return null;
    }

    private void setUpInstanceFieldValue(Map<String, String> parsedQueryString, Class<?> parameterType, Object instance) {
        parsedQueryString.keySet()
                .forEach(queryStringKey -> {
                    try {
                        Field field = parameterType.getDeclaredField(queryStringKey);
                        field.setAccessible(true);
                        field.set(instance, TypeConversionUtil.convertStringToTargetType(parsedQueryString.get(queryStringKey), field.getType()));
                    } catch (NoSuchFieldException e) {
                        throw new IllegalArgumentException("%s에 해당하는 객체 필드명이 없습니다. QueryString의 key와 객체 필드명은 일치해야 합니다.".formatted(queryStringKey), e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
