package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PathVariableArgumentResolverTest {
	private static class ParameterProvider {
		@RequestMapping("/users/{id}")
		public void method(@PathVariable long id) {}

		@RequestMapping("/users/{newId}")
		public void method2(@PathVariable(name = "newId") long id) {}

		@RequestMapping("/users/{id}")
		public void method3(@PathVariable long invalidId) {}
	}

	private static Method getMethodFixture(String methodName) {
		try {
			return ParameterProvider.class.getMethod(methodName, long.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static Parameter getParameterFixture(Method method) {
		return method.getParameters()[0];
	}

	@DisplayName("@PathVariable 애노테이션이 있는 파라미터만 지원한다")
	@Test
	void supports() {
		Method method = getMethodFixture("method");
		Parameter parameter = getParameterFixture(method);

		PathVariableArgumentResolver pathVariableArgumentResolver = new PathVariableArgumentResolver();
		assertThat(pathVariableArgumentResolver.supports(parameter)).isTrue();
	}

	@DisplayName("@RequestMapping의 /users/{id}와 같은 형태와, 요청 받은 /users/1과 같은 URI를 비교해서, {id}에 해당하는 값을 찾아준다.")
	@Test
	void resolve() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURI()).thenReturn("/users/1");

		HttpServletResponse response = mock(HttpServletResponse.class);

		Method method = getMethodFixture("method");
		Parameter parameter = getParameterFixture(method);

		PathVariableArgumentResolver pathVariableArgumentResolver = new PathVariableArgumentResolver();
		Object resolved = pathVariableArgumentResolver.resolve(parameter, method, request, response);

		assertThat(resolved).isInstanceOf(Long.class);
		assertThat((Long) resolved).isEqualTo(1);
	}

	@DisplayName("@PathVariable의 name 옵션을 통해서도 값을 바인딩 할 수 있다.")
	@Test
	void resolve2() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURI()).thenReturn("/users/1");

		HttpServletResponse response = mock(HttpServletResponse.class);

		Method method = getMethodFixture("method2");
		Parameter parameter = getParameterFixture(method);

		PathVariableArgumentResolver pathVariableArgumentResolver = new PathVariableArgumentResolver();
		Object resolved = pathVariableArgumentResolver.resolve(parameter, method, request, response);

		assertThat(resolved).isInstanceOf(Long.class);
		assertThat((Long) resolved).isEqualTo(1);
	}

	@DisplayName("URI 패턴 이름에 일치하는 파라미터가 없을 경우 예외가 발생한다.")
	@Test
	void resolveException() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURI()).thenReturn("/users/1");

		HttpServletResponse response = mock(HttpServletResponse.class);

		Method method = getMethodFixture("method3");
		Parameter parameter = getParameterFixture(method);

		PathVariableArgumentResolver pathVariableArgumentResolver = new PathVariableArgumentResolver();
		assertThatThrownBy(() -> pathVariableArgumentResolver.resolve(parameter, method, request, response))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("URI 패턴의 이름에 일치하는 파라미터가 없습니다. uriPattern=/users/{id}, parameterName=invalidId");
	}
}