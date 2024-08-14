package com.interface21.webmvc.servlet.mvc.tobe.support;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathPatternParser {

    private static final String VARIABLE_REGEX = "\\{([^/]+?)\\}";
    private static final String VARIABLE_REGEX_REPLACEMENT = "([^/]+)";
    private static final int VARIABLE_GROUP_INDEX = 1;

    private final Pattern pattern;
    private final List<String> variableNames = new ArrayList<>();

    public PathPatternParser(String pattern) {
        String regex = buildRegex(pattern);
        this.pattern = Pattern.compile(regex);
    }

    private String buildRegex(final String pattern) {
        final StringBuilder stringBuilder = new StringBuilder();
        final Matcher matcher = Pattern.compile(VARIABLE_REGEX).matcher(pattern);

        while (matcher.find()) {
            matcher.appendReplacement(stringBuilder, VARIABLE_REGEX_REPLACEMENT);
            variableNames.add(matcher.group(VARIABLE_GROUP_INDEX));
        }

        matcher.appendTail(stringBuilder);

        return stringBuilder.toString();
    }

    public boolean matches(final String path) {
        return pattern.matcher(path).matches();
    }

    public Map<String, String> extractUriVariables(final String path) {
        final Matcher matcher = pattern.matcher(path);
        if (!matcher.matches()) {
            return Collections.emptyMap();
        }

        final Map<String, String> variables = new HashMap<>();

        for (int i = 0; i < variableNames.size(); i++) {
            variables.put(variableNames.get(i), matcher.group(i + 1));
        }

        return variables;
    }
}
