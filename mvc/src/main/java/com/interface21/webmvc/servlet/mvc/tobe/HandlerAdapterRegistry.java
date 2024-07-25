package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;

public class HandlerAdapterRegistry {
	private final List<HandlerAdapter> handlerAdapters;

	public HandlerAdapterRegistry(HandlerAdapter handlerAdapter) {
		this.handlerAdapters = Arrays.asList(handlerAdapter);
	}

	public ModelAndView handle(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
		return handlerAdapter.handle(request, response, handler);
	}

	private HandlerAdapter getHandlerAdapter(Object handler) {
		return handlerAdapters.stream()
				.filter(handlerAdapter -> handlerAdapter.supports(handler))
				.findAny()
				.orElseThrow(() -> new IllegalStateException("지원가능한 adapter가 없습니다."));
	}

	public void add(final HandlerAdapter handlerAdapter) {
		if (handlerAdapters.contains(handlerAdapter)) {
			throw new IllegalArgumentException("이미 등록된 handlerAdapter 입니다.");
		}
		handlerAdapters.add(handlerAdapter);
	}
}
