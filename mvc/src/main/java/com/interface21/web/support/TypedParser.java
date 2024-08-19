package com.interface21.web.support;


import java.util.function.Function;

public class TypedParser {
    protected static final TypedParser STRING = new TypedParser(String.class, (s) -> s);
    protected static final TypedParser INT = new TypedParser(int.class, Integer::parseInt);
    protected static final TypedParser WRAPPER_INTEGER = new TypedParser(Integer.class, Integer::parseInt);
    protected static final TypedParser LONG = new TypedParser(long.class, Long::parseLong);
    protected static final TypedParser WRAPPER_LONG = new TypedParser(Long.class, Integer::parseInt);
    protected static final TypedParser BOOLEAN = new TypedParser(boolean.class, Boolean::parseBoolean);
    protected static final TypedParser WRAPPER_BOOLEAN = new TypedParser(Boolean.class, Boolean::parseBoolean);
    protected static final TypedParser SHORT = new TypedParser(short.class, Short::parseShort);
    protected static final TypedParser WRAPPER_SHORT = new TypedParser(Short.class, Short::parseShort);
    protected static final TypedParser FLOAT = new TypedParser(float.class, Float::parseFloat);
    protected static final TypedParser WRAPPER_FLOAT = new TypedParser(Float.class, Float::parseFloat);
    protected static final TypedParser DOUBLE = new TypedParser(double.class, Double::parseDouble);
    protected static final TypedParser WRAPPER_DOUBLE = new TypedParser(Double.class, Double::parseDouble);
    protected static final TypedParser CHAR = new TypedParser(char.class,  s -> s.charAt(0));
    protected static final TypedParser WRAPPER_CHAR = new TypedParser(Character.class,  s -> s.charAt(0));

    private final Class<?> type;
    private final Function<String, Object> parser;

    public TypedParser(Class<?> type, Function<String, Object> parser) {
        this.type = type;
        this.parser = parser;
    }

    protected boolean canParse(Class<?> type) {
        return type.equals(this.type);
    }

    protected Object parse(String value) {
        return parser.apply(value);
    }


}