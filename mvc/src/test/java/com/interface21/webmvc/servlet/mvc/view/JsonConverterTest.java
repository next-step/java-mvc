package com.interface21.webmvc.servlet.mvc.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JsonConverterTest {
    private record User(String name, Integer age) {}

    @DisplayName("Map<String, ?> model 의 size가 0인 경우 빈 Json이 응답된다.")
    @Test
    void convertToJsonWhenModelIsEmpty() {
        JsonConverter jsonConverter = new JsonConverter();
        Map<String, User> modelMap = Map.of();

        String json = jsonConverter.convertModelToJson(modelMap);

        assertThat(json).isEqualTo("{}");
    }

    @DisplayName("Map<String, ?> model을 JSON으로 변환한다.")
    @Test
    void convertToJsonWhenModelIsNotEmpty() {
        JsonConverter jsonConverter = new JsonConverter();
        Map<String, Object> modelMap = Map.of(
                "user", new User("kim", 100),
                "nextstep", "jwp"
        );

        String actual = jsonConverter.convertModelToJson(modelMap);
        CharSequence[] expected = {"\"nextstep\":\"jwp\"", "\"user\":{\"name\":\"kim\",\"age\":100}"};

        assertThat(actual).contains(expected);
    }
}