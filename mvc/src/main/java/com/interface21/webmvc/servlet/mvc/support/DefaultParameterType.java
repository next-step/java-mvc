package com.interface21.webmvc.servlet.mvc.support;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DefaultParameterType {
  BOOLEAN(boolean.class, Boolean.class, Boolean::parseBoolean),
  BYTE(byte.class, Byte.class, Byte::parseByte),
  CHAR(char.class, Character.class, s -> s.charAt(0)),
  DOUBLE(double.class, Double.class, Double::parseDouble),
  FLOAT(float.class, Float.class, Float::parseFloat),
  INT(int.class, Integer.class, Integer::parseInt),
  LONG(long.class, Long.class, Long::parseLong),
  SHORT(short.class, Short.class, Short::parseShort),
  STRING(String.class, String.class, Function.identity());

  private final Class<?> primitiveType;
  private final Class<?> wrapperType;
  private final Function<String, ?> converter;

  private static final Map<Class<?>, DefaultParameterType> PRIMITIVE_TYPE_MAP = Arrays.stream(values())
      .collect(Collectors.toMap(simpleArgument -> simpleArgument.primitiveType, Function.identity()));

  private static final Map<Class<?>, DefaultParameterType> WRAPPER_TYPE_MAP = Arrays.stream(values())
      .collect(Collectors.toMap(simpleArgument -> simpleArgument.wrapperType, Function.identity()));

  DefaultParameterType(final Class<?> primitiveType, final Class<?> wrapperType, final Function<String, ?> converter) {
    this.primitiveType = primitiveType;
    this.wrapperType = wrapperType;
    this.converter = converter;
  }

  public static Object convert(Class<?> type, String variableValue) {
    if(variableValue == null || variableValue.isEmpty()) {
      return null;
    }

    if (type.isPrimitive()) { // null 일 때 처리 ?
      return PRIMITIVE_TYPE_MAP.get(type).converter.apply(variableValue);
    }

    return WRAPPER_TYPE_MAP.get(type).converter.apply(variableValue);
  }
}
