package com.interface21.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JsonUtils 클래스의")
class JsonUtilsTest {

    @DisplayName("toJson 메서드는 객체를 JSON 문자열로 변환한다.")
    @Test
    void testToJson() throws JsonProcessingException {
        // given
        final Map<String, Object> map = Map.of("key", "value");

        // when
        final String json = JsonUtils.toJson(map);

        // then
        assertEquals("{\"key\":\"value\"}", json);
    }
}
