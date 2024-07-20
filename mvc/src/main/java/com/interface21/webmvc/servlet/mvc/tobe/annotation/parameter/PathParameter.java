package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;

import java.lang.reflect.Parameter;

public class PathParameter {

    private static final String EMPTY_PATH_VALUE = "";

    private final String urlPattern;
    private final boolean isPathVariable;
    private final String pathVariableValue;

    private PathParameter(String urlPattern, boolean isPathVariable, String pathVariableValue) {
        this.urlPattern = urlPattern;
        this.isPathVariable = isPathVariable;
        this.pathVariableValue = pathVariableValue;
    }

    public static PathParameter of(String urlPattern, Parameter parameter) {
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
            return new PathParameter(urlPattern, true, pathVariable.value());
        }
        return new PathParameter(urlPattern, false, EMPTY_PATH_VALUE);
    }

    public String parsePathVariable(String url, String parameterName) {
        if (!isPathVariable) {
            throw new IllegalStateException("PathVariable이 없는 경우 호출할 수 없다");
        }
        if (pathVariableValue.equals(EMPTY_PATH_VALUE)) {
            return PathPatternUtil.getUriValue(urlPattern, url, parameterName);
        }
        return PathPatternUtil.getUriValue(urlPattern, url, pathVariableValue);
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public boolean isPathVariable() {
        return isPathVariable;
    }

    public String getPathVariableValue() {
        return pathVariableValue;
    }
}
