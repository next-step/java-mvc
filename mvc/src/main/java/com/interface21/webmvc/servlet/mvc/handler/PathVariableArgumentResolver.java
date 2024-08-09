package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.mvc.support.PathPatternUtil;
import com.interface21.webmvc.servlet.mvc.support.TypeConversionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class PathVariableArgumentResolver implements ArgumentResolver{
	@Override
	public boolean supports(final Parameter parameter) {
		return parameter.isAnnotationPresent(PathVariable.class);
	}

	@Override
	public Object resolve(final Parameter parameter, final Method method, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
		String uriPattern = requestMapping.value();
		String requestURI = request.getRequestURI();

		String pathVariableName = getPathVariableName(parameter);
		String uriValue = PathPatternUtil.getUriValue(uriPattern, requestURI, pathVariableName);
		validateUriValue(uriValue, uriPattern, pathVariableName);
		return TypeConversionUtil.convertStringToTargetType(uriValue, parameter.getType());
	}

	private void validateUriValue(final String uriValue, final String uriPattern, final String pathVariableName) {
		if (uriValue == null) {
			throw new IllegalArgumentException("URI 패턴의 이름에 일치하는 파라미터가 없습니다. uriPattern=%s, parameterName=%s".formatted(uriPattern, pathVariableName));
		}
	}

	private String getPathVariableName(final Parameter parameter) {
		PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
		if (pathVariable.name().isBlank()) {
			return parameter.getName();
		}
		return pathVariable.name();
	}
}
