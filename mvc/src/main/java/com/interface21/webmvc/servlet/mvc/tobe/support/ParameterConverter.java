package com.interface21.webmvc.servlet.mvc.tobe.support;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

public class ParameterConverter {
    public static Object[] convertToMethodArg(
            Parameter[] parameters,
            Map<String, String[]> parameterMap
    ) {
        if (isDtoParam(parameters)) {
            return convertToDto(parameters, parameterMap);
        }

        return convertToPrimitive(parameters, parameterMap);
    }

    private static boolean isDtoParam(Parameter[] parameters) {
        // dto는 하나만 사용할 수 있게 처리
        if (parameters.length > 1) {
            return false;
        }

        // 만약 primitive 타입이거나 wrapper class, 배열이 아닐 경우, 필드 매핑을 해줘야하기때문에 dto인지 판단
        return !parameters[0].getClass().isArray()
                && !(isPrimitiveOrPrimitiveWrapperOrString(parameters[0].getType()));
    }

    // 출처 https://stackoverflow.com/questions/25039080/java-how-to-determine-if-type-is-any-of-primitive-wrapper-string-or-something
    private static boolean isPrimitiveOrPrimitiveWrapperOrString(Class<?> type) {
        return (type.isPrimitive() && type != void.class) ||
                type == Double.class || type == Float.class || type == Long.class ||
                type == Integer.class || type == Short.class || type == Character.class ||
                type == Byte.class || type == Boolean.class || type == String.class;
    }

    private static Object[] convertToDto(Parameter[] parameters, Map<String, String[]> parameterMap) {
        Parameter parameter = parameters[0];
        Class<?> clazz = parameter.getType();
        Object[] args = new Object[1];
        Object[] convertedField = new Object[clazz.getFields().length];
        for (int i = 0; i < clazz.getFields().length; i++) {
            Field field = clazz.getFields()[i];
        }

        return args;
    }

    private static Object[] convertToPrimitive(Parameter[] parameters, Map<String, String[]> parameterMap) {
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.getType().isArray()) {
                args[i] = convertArray(parameter.getName(), parameter.getType().getComponentType(), parameterMap);
            } else {
                args[i] = convertVariable(parameter.getName(), parameter.getType(), parameterMap);
            }
        }
        return args;
    }

    private static Object[] convertArray(
            String parameterName,
            Class<?> compositionType,
            Map<String, String[]> parameterMap
    ) {
        String[] values = parameterMap.get(parameterName);
        return Arrays.stream(values)
                .map(v -> convertValue(compositionType, v))
                .toArray();
    }

    private static Object convertVariable(
            String parameterName,
            Class<?> clazz,
            Map<String, String[]> parameterMap
    ) {
        String value = parameterMap.get(parameterName)[0];
        return convertValue(clazz, value);
    }

    private static Object convertValue(Class<?> clazz, String value) {
        if( Boolean.class == clazz || Boolean.TYPE == clazz) return Boolean.parseBoolean( value );
        if( Byte.class == clazz || Byte.TYPE == clazz) return Byte.parseByte( value );
        if( Short.class == clazz || Short.TYPE == clazz) return Short.parseShort( value );
        if( Integer.class == clazz || Integer.TYPE == clazz) return Integer.parseInt( value );
        if( Long.class == clazz || Long.TYPE == clazz) return Long.parseLong( value );
        if( Float.class == clazz || Float.TYPE == clazz) return Float.parseFloat( value );
        if( Double.class == clazz || Double.TYPE == clazz) return Double.parseDouble( value );
        return value;
    }
}
