package com.interface21.webmvc.servlet.mvc.tobe.support;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathPatternParser {

    private final Pattern pattern;
    private final List<String> variableNames = new ArrayList<>();

    public PathPatternParser(String pattern) {
        String regex = buildRegex(pattern);
        this.pattern = Pattern.compile(regex);
    }

    private String buildRegex(String pattern) {
        Matcher matcher = Pattern.compile("\\{([^}]+)}").matcher(pattern);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            variableNames.add(matcher.group(1));
            matcher.appendReplacement(buffer, "([^/]+)");
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
            return Collections.emptyMap();
        }
        Map<String, String> variables = new HashMap<>();
        for (int i = 0; i < variableNames.size(); i++) {
            variables.put(variableNames.get(i), matcher.group(i + 1));
        }
        return variables;
    }
}
