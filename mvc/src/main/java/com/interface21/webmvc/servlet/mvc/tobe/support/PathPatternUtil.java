package com.interface21.webmvc.servlet.mvc.tobe.support;

import java.util.Map;

public class PathPatternUtil {

    public static String getUriValue(final String pattern, final String path, final String key) {
        final PathPatternParser parser = new PathPatternParser(pattern);
        final Map<String, String> uriVariables = parser.extractUriVariables(path);
        return uriVariables.get(key);
    }

    public static Map<String, String> getUriVariables(final String pattern, final String path) {
        final PathPatternParser parser = new PathPatternParser(pattern);
        return parser.extractUriVariables(path);
    }

    public static boolean isUrlMatch(final String pattern, final String path) {
        final PathPatternParser parser = new PathPatternParser(pattern);
        return parser.matches(path);
    }
}
