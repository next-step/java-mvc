package com.interface21.webmvc.servlet.mvc.tobe.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PathPatternParser {

    private final Pattern pattern;
    private final List<String> variableNames = new ArrayList<>();

    public PathPatternParser(String pattern) {
        String regex = buildRegex(pattern);
        this.pattern = Pattern.compile(regex);
    }

    private String buildRegex(String pattern) {
        return null;
    }

    public boolean matches(String path) {
        return false;
    }

    public Map<String, String> extractUriVariables(String path) {
        return null;
    }
}
