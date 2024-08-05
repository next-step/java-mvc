package com.interface21.webmvc.servlet.mvc.tobe.support;

import java.util.Map;

public class PathPatternUtil {

    public static String getUriValue(String pattern, String path, String key) {
        return getUriVariables(pattern, path).get(key);
    }

    public static Map<String, String> getUriVariables(String pattern, String path) {
        var pathPatternParser = new PathPatternParser(pattern);
        return pathPatternParser.extractUriVariables(path);
    }

    public static boolean isUrlMatch(String pattern, String path) {
        return new PathPatternParser(pattern).matches(path);
    }
}
