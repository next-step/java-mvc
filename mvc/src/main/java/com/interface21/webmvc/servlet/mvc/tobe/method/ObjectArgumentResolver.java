package com.interface21.webmvc.servlet.mvc.tobe.method;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ObjectArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return !SimpleArgument.isSimpleParameter(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Class<?> aClass = methodParameter.getParameterType();
        Object object = createObject(aClass);
        setFields(object, aClass.getDeclaredFields(), httpServletRequest);

        return object;
    }

    private Object createObject(Class<?> aClass) {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("타입 " + aClass.getName() + "의 객체를 인스턴스화할 수 없습니다.");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("타입 " + aClass.getName() + "의 객체에 접근할 수 없습니다.");
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("타입 " + aClass.getName() + "의 객체를 생성할 수 없습니다.");
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("타입 " + aClass.getName() + "의 생성자를 찾을 수 없습니다.");
        }
    }

    private void setFields(Object object, Field[] fields, HttpServletRequest httpServletRequest) {
        Arrays.stream(fields).forEach(field -> setField(object, httpServletRequest, field));
    }

    private static void setField(Object object, HttpServletRequest httpServletRequest, Field field) {
        field.setAccessible(true);
        String parameterValue = httpServletRequest.getParameter(field.getName());
        Object value = SimpleArgument.convert(field.getType(), parameterValue);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("필드 " + field.getName() + "에 접근할 수 없습니다.");
        }
    }
}
