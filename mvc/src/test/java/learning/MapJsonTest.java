package learning;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MapJsonTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    private record User(String id) {}

    @DisplayName("Map을 JSON으로 변환할 수 있다.")
    @Test
    void test() throws JsonProcessingException {
        Map<String, ?> map = Map.of("user", new User("id"), "haha", "hoho");

        String json = objectMapper.writeValueAsString(map);

        assertThat(json).contains("\"user\":{\"id\":\"id\"}", "\"haha\":\"hoho\"");
    }
}
