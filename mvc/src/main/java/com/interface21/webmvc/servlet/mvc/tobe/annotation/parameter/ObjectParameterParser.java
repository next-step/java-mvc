package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Field;

import static com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter.SupportParameterType.parse;

public class ObjectParameterParser implements MethodParameterParser {

    private final Class<?> parameterType;

    public ObjectParameterParser(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    @Override
    public boolean accept() {
        throw new IllegalStateException("Object parser는 지원가능여부를 호출할 수 없습니다.");
    }

    @Override
    public Object parseValue(HttpServletRequest request, HttpServletResponse response) {
        try {
            return parseObject(request);
        } catch (Exception e) {
            throw new RuntimeException("객체 파싱에서 문제가 발생했습니다.", e);
        }
    }

    private Object parseObject(HttpServletRequest request) throws Exception {
        Object instance = parameterType.getDeclaredConstructor()
                .newInstance();
        for (Field field : parameterType.getDeclaredFields()) {
            field.setAccessible(true);
            field.set(instance, parse(field.getType(), request.getParameter(field.getName())));
            field.setAccessible(false);
        }
        return instance;
    }
}
