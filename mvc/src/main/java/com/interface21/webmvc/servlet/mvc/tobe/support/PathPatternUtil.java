package com.interface21.webmvc.servlet.mvc.tobe.support;

import static java.util.function.Predicate.not;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class PathPatternUtil {

    private static final Pattern BRACKET = Pattern.compile("\\{([a-zA-Z-0-9]+)\\}");
    private static final String SLASH = "\\/";

    public static String getUriValue(String pattern, String path, String key) {
        assert pattern != null;
        assert path != null;
        assert key != null;

        var patterns = split(pattern, SLASH);
        var values = split(path, SLASH);

        for (int i = 0; i < patterns.length; i++) {
            if (isMatchedKey(patterns[i], key)) {
                return values[i];
            }
        }
        return null;
    }

    public static Map<String, String> getUriVariables(String pattern, String path) {
        assert pattern != null;
        assert path != null;
        assert PathPatternUtil.isUrlMatch(pattern, path);

        var patterns = split(pattern, SLASH);
        var paths = split(path, SLASH);

        var map = new HashMap<String, String>();
        for (int i = 0; i < patterns.length; i++) {
            var pattern1 = patterns[i];
            var path1 = paths[i];

            var matcher = BRACKET.matcher(pattern1);
            if (matcher.find()) {
                var key = matcher.group(1);
                map.put(key, getUriValue(pattern1, path1, key));
            }
        }
        return map;
    }

    public static boolean isUrlMatch(String pattern, String path) {

        var patterns = split(pattern, SLASH);
        var paths = split(path, SLASH);
        if (patterns.length != paths.length) {
            return false;
        }

        return IntStream.range(0, patterns.length)
                .allMatch(
                        i -> {
                            if (BRACKET.matcher(patterns[i]).find()) {
                                return true;
                            }
                            return Objects.equals(patterns[i], paths[i]);
                        });
    }

    private static String[] split(String pattern, String delimiter) {
        return Arrays.stream(pattern.split(delimiter))
                .filter(not(String::isEmpty))
                .toArray(String[]::new);
    }

    private static boolean isMatchedKey(String pattern, String key) {
        var matcher = BRACKET.matcher(pattern);
        return matcher.find() && pattern.equals(pattern.replace(matcher.group(1), key));
    }
}
