package com.interface21.webmvc.servlet.mvc.support;

import java.util.Map;

public class PathPatternUtil {
    public static String getUriValue(String pattern, String path, String key) {
        Map<String, String> uriVariables = getUriVariables(pattern, path);
        return uriVariables.get(key);
    }

    public static Map<String, String> getUriVariables(String pattern, String path) {
        PathPatternParser pathPatternParser = new PathPatternParser(pattern);
        return pathPatternParser.extractUriVariables(path);
    }

    public static boolean isUrlMatch(String pattern, String path) {
        PathPatternParser pathPatternParser = new PathPatternParser(pattern);
        return pathPatternParser.matches(path);
    }
}
