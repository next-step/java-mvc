package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class HandlerAdapterRegistryTest {

	private final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry(new AnnotationMethodHandlerAdapter());
	private final HttpServletRequest request = mock(HttpServletRequest.class);
	private final HttpServletResponse response = mock(HttpServletResponse.class);

	@DisplayName("adapter 실행")
	@Test
	void handle() throws Exception {
		HandlerExecution handlerExecution = new HandlerExecution(new TestController(), "findUserId");

		ModelAndView actual = handlerAdapterRegistry.handle(handlerExecution, request, response);
		assertThat(actual.getView()).isEqualTo(new JspView(""));
	}

	@DisplayName("adapter 실행시 지원가능한_adapter가 없으면 예외가 발생한다")
	@Test
	void handleNotMatchHandler() {
		assertThatThrownBy(() -> handlerAdapterRegistry.handle("error-handler", request, response))
				.isInstanceOf(IllegalStateException.class)
				.hasMessage("지원가능한 adapter가 없습니다.");
	}
}
