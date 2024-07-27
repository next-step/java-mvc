package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HandlerMappingRegistryTest {
	private final HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry(new AnnotationHandlerMapping("samples"));
	private final HttpServletRequest request = mock(HttpServletRequest.class);

	@BeforeEach
	void setUp() {
		this.handlerMappingRegistry.initialize();
	}

	@DisplayName("지원하는 URI로 호출 한다.")
	@Test
	void getHandler() {
		when(request.getRequestURI()).thenReturn("/get-test");
		when(request.getMethod()).thenReturn("GET");

		Object actual = handlerMappingRegistry.getHandler(request);
		assertThat(actual).isInstanceOf(HandlerExecution.class);
	}

	@DisplayName("지원하지 않은 URI로 호출하면 예외가 발생한다")
	@Test
	void getHandlerNotMatchRequestURI() {
		when(request.getRequestURI()).thenReturn("/error_path");
		when(request.getMethod()).thenReturn("GET");

		assertThatThrownBy(() -> handlerMappingRegistry.getHandler(request))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("요청된 request를 처리할 수 있는 handler가 없습니다.");
	}

	@DisplayName("중복되는 handlerMapping을 추가하면 예외가 발생한다.")
	@Test
	void addDuplicatedHandlerMapping() {
		AnnotationHandlerMapping samples = new AnnotationHandlerMapping("samples");
		samples.initialize();
		assertThatThrownBy(() -> handlerMappingRegistry.add(samples))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("이미 등록된 handler mapping 입니다.");
	}
}
