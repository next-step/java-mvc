package com.interface21.webmvc.servlet.mvc.tobe.support;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParameterConverter {
    public static Object[] convertToMethodArg(
            Method method,
            String uri,
            Map<String, String[]> parameterMap
    ) {
        if (isDtoParam(method.getParameters())) {
            return convertToDto(method.getParameters(), parameterMap);
        }

        return convertToPrimitive(method, uri, parameterMap);
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
        Map<String, Object> convertedFieldByName = new HashMap<>();
        Map<String, Class<?>> fieldTypeByName = new HashMap<>();

        for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
            Field field = clazz.getDeclaredFields()[i];
            convertedFieldByName.put(field.getName(), convertVariable(field.getName(), field.getType(), parameterMap));
            fieldTypeByName.put(field.getName(), field.getType());
        }

        args[0] = getNewInstance(convertedFieldByName, fieldTypeByName, clazz);

        return args;
    }

    private static Object getNewInstance(
            Map<String, Object> convertedFieldByName,
            Map<String, Class<?>> fieldTypeByName,
            Class<?> clazz
    ) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();
            boolean isPossibleToGetNewInstance = true;
            Object[] args = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (!convertedFieldByName.containsKey(parameter.getName())) {
                    isPossibleToGetNewInstance = false;
                    break;
                }

                if (!fieldTypeByName.containsKey(parameter.getName())) {
                    isPossibleToGetNewInstance = false;
                    break;
                }

                if (!fieldTypeByName.get(parameter.getName()).equals(parameter.getType())) {
                    isPossibleToGetNewInstance = false;
                    break;
                }

                args[i] = convertedFieldByName.get(parameter.getName());
            }

            if (isPossibleToGetNewInstance) {
                try {
                    return constructor.newInstance(args);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new RuntimeException("해당하는 생성자가 없습니다");
    }

    private static Object[] convertToPrimitive(Method method, String uri, Map<String, String[]> parameterMap) {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
            if (pathVariable != null) {
                args[i] = convertPathVariable(method, uri, parameter);
            } else if (parameter.getType().isArray()) {
                args[i] = convertArray(parameter.getName(), parameter.getType().getComponentType(), parameterMap);
            } else {
                args[i] = convertVariable(parameter.getName(), parameter.getType(), parameterMap);
            }
        }
        return args;
    }

    private static Object convertPathVariable(Method method, String uri, Parameter parameter) {
        String uriPattern = method.getAnnotation(RequestMapping.class).value();
        return convertValue(
                parameter.getType(),
                PathPatternUtil.getUriValue(uriPattern, uri, parameter.getName())
        );
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
