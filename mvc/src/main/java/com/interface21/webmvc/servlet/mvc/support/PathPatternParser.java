package com.interface21.webmvc.servlet.mvc.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PathPatternParser {

    private final Pattern pattern;
    private final List<String> variableNames = new ArrayList<>();

    public PathPatternParser(String pattern) {
        String regex = buildRegex(pattern);
        this.pattern = Pattern.compile(regex);
    }

    private String buildRegex(String pattern) {
        Pattern compiledPattern = Pattern.compile("\\{([^}]+)}");
        Matcher matcher = compiledPattern.matcher(pattern);
        variableNames.addAll(extractPathVariableNames(matcher));
        return matcher.replaceAll("([^/]+)");
    }

    private List<String> extractPathVariableNames(final Matcher matcher) {
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }

    public boolean matches(String path) {
        return pattern.matcher(path).matches();
    }

    public Map<String, String> extractUriVariables(String path) {
        Matcher matcher = pattern.matcher(path);
        if (!matcher.matches()) {
            return Map.of();
        }
        return combinePathVariableNameAndValue(matcher);
    }

    private Map<String, String> combinePathVariableNameAndValue(Matcher matcher) {
        List<String> pathVariableValues = extractPathVariableValues(matcher);

        return IntStream.range(0, variableNames.size())
                .boxed()
                .collect(Collectors.toUnmodifiableMap(
                        variableNames::get,
                        pathVariableValues::get
                ));
    }

    private List<String> extractPathVariableValues(Matcher matcher) {
        if (!matcher.matches()) {
            return List.of();
        }

        return IntStream.rangeClosed(1, variableNames.size())
                .mapToObj(matcher::group)
                .toList();
    }
}
