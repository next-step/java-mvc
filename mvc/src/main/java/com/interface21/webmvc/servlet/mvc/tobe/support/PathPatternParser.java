package com.interface21.webmvc.servlet.mvc.tobe.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathPatternParser {

    private static final Pattern PATH_VARIABLE_PATTERN = Pattern.compile("\\{([^}]+)}");
    private static final int PATTERN_GROUP_INDEX = 1;
    private static final String REPLACEMENT = "([^/]+)";

    private final Pattern pattern;
    private final List<String> variableNames = new ArrayList<>();

    public PathPatternParser(final String pattern) {
        final String regex = buildRegex(pattern);
        this.pattern = Pattern.compile(regex);
    }

    private String buildRegex(final String pattern) {
        final Matcher matcher = PATH_VARIABLE_PATTERN.matcher(pattern);
        final StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            variableNames.add(matcher.group(PATTERN_GROUP_INDEX));
            matcher.appendReplacement(buffer, REPLACEMENT);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
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
