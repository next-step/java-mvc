package com.interface21.webmvc.servlet.view;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JsonViewTest {

    @Test
    @DisplayName("JsonView는 Json 형식으로 객체를 변환할 수 있다.")
    void render() throws Exception {
        // given
        JsonView jsonView = new JsonView();
        User user = new User("test", 20);
        PrintWriter writer = mock(PrintWriter.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        given(response.getWriter()).willReturn(writer);

        // when
        jsonView.render(Map.of("user", user), null, response);

        // then
        verify(writer).write("{\"name\":\"test\",\"age\":20}");
    }

    @Test
    @DisplayName("JsonView는 두 개 이상의 객체를 Map 형태로 변환할 수 있다.")
    void render_moreThanTwoObjects() throws Exception {
        // given
        JsonView jsonView = new JsonView();
        User user = new User("test", 20);
        Order order = new Order("test", 1000);
        PrintWriter writer = mock(PrintWriter.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        given(response.getWriter()).willReturn(writer);

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("user", user);
        model.put("order", order);

        // when
        jsonView.render(model, null, response);

        // then
        verify(writer).write("{\"user\":{\"name\":\"test\",\"age\":20},\"order\":{\"orderName\":\"test\",\"price\":1000}}");

    }

    record User(String name, int age) {

    }

    record Order(String orderName, int price) {

    }
}
