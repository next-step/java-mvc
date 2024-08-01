package com.interface21.webmvc.servlet.mvc.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathPatternParser {

    private static final String PATH_VARIABLE_PATTERN = "\\{([^}]+)}";
    private static final String PATH_VARIABLE_REGEX = "([^/]+)";
    private static final int GROUP_INDEX = 1;
    private static final int GROUP_OFFSET = 1;

    private final Pattern pattern;
    private final List<String> variableNames;

    public PathPatternParser(String pattern) {
        this.variableNames = new ArrayList<>();
        this.pattern = Pattern.compile(buildRegex(pattern));
    }

    private String buildRegex(String pattern) {
        Matcher matcher = Pattern.compile(PATH_VARIABLE_PATTERN).matcher(pattern);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            variableNames.add(matcher.group(GROUP_INDEX));
            matcher.appendReplacement(buffer, PATH_VARIABLE_REGEX);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    public boolean matches(String path) {
        return pattern.matcher(path).matches();
    }

    public Map<String, String> extractUriVariables(String path) {
        Matcher matcher = pattern.matcher(path);
        if (!matcher.matches()) {
            return Map.of();
        }
        Map<String, String> variables = new HashMap<>();
        for (int i = 0; i < variableNames.size(); i++) {
            variables.put(variableNames.get(i), matcher.group(i + GROUP_OFFSET));
        }
        return variables;
    }
}
