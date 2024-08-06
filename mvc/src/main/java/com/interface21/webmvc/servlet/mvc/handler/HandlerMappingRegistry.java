package com.interface21.webmvc.servlet.mvc.handler;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HandlerMappingRegistry {
	private final List<HandlerMapping> handlerMappings;

	public HandlerMappingRegistry(HandlerMapping handlerMapping) {
		this.handlerMappings = Arrays.asList(handlerMapping);
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

	public void add(final HandlerMapping handlerMapping) {
		if(handlerMappings.contains(handlerMapping)) {
			throw new IllegalArgumentException("이미 등록된 handler mapping 입니다.");
		}
		handlerMappings.add(handlerMapping);
	}
}
