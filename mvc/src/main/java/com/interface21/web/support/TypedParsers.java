package com.interface21.web.support;

import java.util.ArrayList;
import java.util.List;

public class TypedParsers {
    private static List<TypedParser> parserList = new ArrayList<>(List.of(TypedParser.STRING, TypedParser.INT, TypedParser.WRAPPER_INTEGER, TypedParser.LONG, TypedParser.WRAPPER_LONG, TypedParser.BOOLEAN, TypedParser.WRAPPER_BOOLEAN, TypedParser.SHORT, TypedParser.WRAPPER_SHORT, TypedParser.FLOAT, TypedParser.WRAPPER_FLOAT, TypedParser.DOUBLE, TypedParser.WRAPPER_DOUBLE, TypedParser.CHAR, TypedParser.WRAPPER_CHAR));


    public static void add(TypedParser... parsers) {
        parserList.addAll(List.of(parsers));
    }

    public static Object parse(final Class<?> type, final String value) {
        return parserList.stream().filter(parser -> parser.canParse(type)).findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .parse(value);
    }
}
