package com.interface21.webmvc.servlet.mvc.tobe.support;

import static java.util.function.Predicate.not;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class PathPatternParser {
    private static final String KEY_PATTERN = "(?<%s>[a-zA-z-0-9]+)";
    private static final String SLASH_PATTERN = "\\/";
    private static final String WORD_WRAPPED_BRACKET = "\\{[a-zA-z\\-0-9]+\\}";
    private static final String SLASH = "/";

    private final Pattern pattern;
    private final List<String> variableNames = new ArrayList<>();

    public PathPatternParser(String pattern) {
        String regex = buildRegex(pattern);
        this.pattern = Pattern.compile(regex);
    }

    public boolean matches(String path) {
        return pattern.matcher(path).find();
    }

    public Map<String, String> extractUriVariables(String path) {
        var matcher = pattern.matcher(path);
        if (!matcher.find()) {
            return Map.of();
        }
        return variableNames.stream()
                .map(key -> Map.entry(key, matcher.group(key)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String buildRegex(String pattern) {
        return Arrays.stream(pattern.split(SLASH))
                .filter(not(String::isEmpty))
                .map(generatePathVariablePattern())
                .collect(Collectors.joining(SLASH_PATTERN, SLASH_PATTERN, StringUtils.EMPTY));
    }

    private Function<String, String> generatePathVariablePattern() {
        return it -> {
            if (it.matches(WORD_WRAPPED_BRACKET)) {
                var key = removeBracket(it);
                addVariableNames(key);
                return KEY_PATTERN.formatted(key);
            }
            return it;
        };
    }

    private static String removeBracket(String value) {
        return value.replaceAll("[{}]", StringUtils.EMPTY);
    }

    private void addVariableNames(String key) {
        variableNames.add(key);
    }
}
