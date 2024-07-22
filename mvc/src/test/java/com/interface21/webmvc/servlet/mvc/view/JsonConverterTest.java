package com.interface21.webmvc.servlet.mvc.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JsonConverterTest {
    private record User(String name, Integer age) {}

    @DisplayName("Map<String, ?> model 의 size가 0인 경우 빈 Json이 응답된다.")
    @Test
    void test() {
        JsonConverter jsonConverter = new JsonConverter();
        Map<String, User> modelMap = Map.of();

        String json = jsonConverter.convertModelToJson(modelMap);

        assertThat(json).isEqualTo("{}");
    }

    @DisplayName("Map<String, ?> model 의 size가 1인 경우 value 만 JSON으로 변환한다.")
    @Test
    void test2() {
        JsonConverter jsonConverter = new JsonConverter();
        Map<String, User> modelMap = Map.of("user", new User("kim", 100));

        String json = jsonConverter.convertModelToJson(modelMap);

        assertThat(json).isEqualTo("{\"name\":\"kim\",\"age\":100}");
    }

    @DisplayName("Map<String, ?> model 의 size가 2 이상인 경우 Map 자체를 JSON으로 변환한다.")
    @Test
    void test3() {
        JsonConverter jsonConverter = new JsonConverter();
        Map<String, Object> modelMap = Map.of(
                "user", new User("kim", 100),
                "nextstep", "jwp"
        );

        String json = jsonConverter.convertModelToJson(modelMap);

        assertThat(json).contains("\"nextstep\":\"jwp\"", "\"user\":{\"name\":\"kim\",\"age\":100}");
    }
}