package com.interface21.webmvc.servlet.mvc.tobe.support;

import java.util.Map;

public class PathPatternUtil {

    public static String getUriValue(String pattern, String path, String key) {
        final Map<String, String> uriVariables = getUriVariables(pattern, path);
        return uriVariables.get(key);
    }

    public static Map<String, String> getUriVariables(String pattern, String path) {
        if (!isUrlMatch(pattern, path)) {
            return Map.of();
        }
        final PathPatternParser parser = new PathPatternParser(pattern);
        return parser.extractUriVariables(path);
    }

    public static boolean isUrlMatch(String pattern, String path) {
        final PathPatternParser parser = new PathPatternParser(pattern);
        return parser.matches(path);
    }
}
