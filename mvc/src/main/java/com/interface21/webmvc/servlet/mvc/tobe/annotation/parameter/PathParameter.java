package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.webmvc.servlet.mvc.tobe.support.PathPatternUtil;

import java.lang.reflect.Parameter;
import java.util.Objects;

public class PathParameter {

    private static final String EMPTY_PATH_VALUE = "";

    private final String urlPattern;
    private final boolean isPathVariable;
    private final String pathValue;

    public PathParameter(String urlPattern, boolean isPathVariable, String pathValue) {
        this.urlPattern = urlPattern;
        this.isPathVariable = isPathVariable;
        this.pathValue = pathValue;
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
        if (pathValue.equals(EMPTY_PATH_VALUE)) {
            return PathPatternUtil.getUriValue(urlPattern, url, parameterName);
        }
        return PathPatternUtil.getUriValue(urlPattern, url, pathValue);
    }

    public boolean isPathVariable() {
        return isPathVariable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathParameter that = (PathParameter) o;
        return isPathVariable == that.isPathVariable && Objects.equals(urlPattern, that.urlPattern) && Objects.equals(pathValue, that.pathValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlPattern, isPathVariable, pathValue);
    }
}
