package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class ObjectParameterResolver implements ParameterResolver {


    @Override
    public boolean support(Parameter parameter) {
        return false;
    }

    @Override
    public Object parseValue(Parameter parameter, HttpServletRequest request,
        HttpServletResponse response) {
        Class<?> clazz = parameter.getType();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            Arrays.stream(clazz.getDeclaredFields())
                .forEach(
                    field -> {
                        field.setAccessible(true);
                        Object fieldValue = request.getParameter(field.getName());
                        try {
                            field.set(instance, TypeMapper.parse(field.getType(), fieldValue));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("field에 대해 접근 권한이 없습니다.");
                        }
                    }
                );
            return instance;


        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException("객체 파싱을 실패하였습니다..");
        }
    }
}
