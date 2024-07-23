package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HandlerMappingRegistry {
	private final List<HandlerMapping> handlerMappings;

	public HandlerMappingRegistry(HandlerMapping... handlerMappings) {
		this.handlerMappings = Arrays.stream(handlerMappings)
				.toList();
	}

	public void initialize() {
		this.handlerMappings.forEach(HandlerMapping::initialize);
	}

	public Object getHandler(HttpServletRequest request) {
		return handlerMappings.stream()
				.map(handlerMapping -> handlerMapping.getHandler(request))
				.filter(Objects::nonNull)
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("요청된 request를 처리할 수 있는 handler가 없습니다."));
	}
}
