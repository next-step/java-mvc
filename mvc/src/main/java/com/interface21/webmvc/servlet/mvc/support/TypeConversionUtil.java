package com.interface21.webmvc.servlet.mvc.support;

public class TypeConversionUtil {
	private TypeConversionUtil() {
	}

	@SuppressWarnings("unchecked")
	public static  <T> T convertStringToTargetType(String value, Class<?> targetType) {
		if (targetType == Integer.class || targetType == int.class) {
			return (T) Integer.valueOf(value);
		} else if (targetType == Double.class || targetType == double.class) {
			return (T) Double.valueOf(value);
		} else if (targetType == Boolean.class || targetType == boolean.class) {
			return (T) Boolean.valueOf(value);
		} else if (targetType == Long.class || targetType == long.class) {
			return (T) Long.valueOf(value);
		} else if (targetType == Float.class || targetType == float.class) {
			return (T) Float.valueOf(value);
		} else if (targetType == Short.class || targetType == short.class) {
			return (T) Short.valueOf(value);
		} else if (targetType == Byte.class || targetType == byte.class) {
			return (T) Byte.valueOf(value);
		} else if (targetType == String.class) {
			return (T) value;
		} else {
			throw new IllegalArgumentException("String에서 형 변환을 할 수 없는 타입입니다. type=" + targetType.getName());
		}
	}
}
