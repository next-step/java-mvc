package com.interface21.webmvc.servlet.mvc.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class PathPatternUtil {
    public static String getUriValue(String pattern, String path, String key) {
        Map<String, String> uriVariables = getUriVariables(pattern, path);
        return uriVariables.get(key);
    }

    public static Map<String, String> getUriVariables(String pattern, String path) {
        // 이름을 추출
        Pattern compiledPattern = Pattern.compile("\\{([^}]+)}");
        Matcher matcher = compiledPattern.matcher(pattern);
        List<String> pathVariableNames = extractPathVariableNames(matcher);

        // 값을 추출
        String regex = matcher.replaceAll("([^/]+)");
        Pattern compiled = Pattern.compile(regex);
        Matcher matcher1 = compiled.matcher(path);
        List<String> pathVariableValues = extractPathVariableValues(matcher1, pathVariableNames.size());

        // 이름과 값을 조합
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < pathVariableNames.size(); i++) {
            result.put(pathVariableNames.get(i), pathVariableValues.get(i));
        }
        return result;
    }

    private static List<String> extractPathVariableNames(Matcher matcher) {
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }

    private static List<String> extractPathVariableValues(Matcher matcher, int pathVariableKeyCount) {
        if (!matcher.matches()) {
            return List.of();
        }

        return IntStream.rangeClosed(1, pathVariableKeyCount)
                .mapToObj(matcher::group)
                .toList();
    }

    public static boolean isUrlMatch(String pattern, String path) {
        return false;
    }
}
