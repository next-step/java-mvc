package com.interface21.webmvc.servlet.mvc.handler;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ArgumentResolvers {
	private final List<ArgumentResolver> values;

	public ArgumentResolvers() {
		this.values = new ArrayList<>();
	}

	public void add(ArgumentResolver argumentResolver) {
		values.add(argumentResolver);
	}

	public ArgumentResolver findOneSupports(Parameter parameter) {
		return values.stream()
				.filter(argumentResolver -> argumentResolver.supports(parameter))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("지원하는 ArgumentResolver가 없습니다."));
	}
}
