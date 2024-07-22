package learning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <a href="https://www.baeldung.com/jackson-object-mapper-tutorial">참고문서</a>
 */
public class ObjectMapperTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    private record Car(String color, String type) {
    }

    @DisplayName("객체를 JSON 형식으로 변환할 수 있다")
    @Test
    void test() throws JsonProcessingException {
        Car car = new Car("blue", "sports");

        String json = objectMapper.writeValueAsString(car);

        assertThat(json).isEqualTo("{\"color\":\"blue\",\"type\":\"sports\"}");
    }

    @DisplayName("JSON 형식의 데이터를 객체로 변환할 수 있다.")
    @Test
    void test2() throws JsonProcessingException {
        String json = "{\"color\":\"blue\",\"type\":\"sports\"}";

        Car car = objectMapper.readValue(json, Car.class);

        assertThat(car.color()).isEqualTo("blue");
        assertThat(car.type()).isEqualTo("sports");
    }

    @DisplayName("JSON 형식의 데이터를 JsonNode로 변환하면 특정 노드에서 데이터를 검색할 수 있다.")
    @Test
    void test3() throws JsonProcessingException {
        String json = "{\"color\":\"blue\",\"type\":\"sports\"}";

        JsonNode jsonNode = objectMapper.readTree(json);

        assertThat(jsonNode.get("color").asText()).isEqualTo("blue");
    }

    @DisplayName("JSON array string으로 부터 Java List를 만들 수 있다.")
    @Test
    void test4() throws JsonProcessingException {
        String jsonCarArray = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";

        List<Car> cars = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>() {
        });

        assertAll(
                () -> assertThat(cars).hasSize(2),
                () -> assertThat(cars.getFirst().color()).isEqualTo("Black"),
                () -> assertThat(cars.get(1).color()).isEqualTo("Red")
        );
    }

    @DisplayName("JSON string으로 부터 Java Map을 만들 수 있다.")
    @Test
    void test5() throws JsonProcessingException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";

        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });

        assertThat(map).containsAllEntriesOf(Map.of("color", "Black", "type", "BMW"));
    }

    @DisplayName("JSON string에, 객체에 없는 새로운 필드가 추가되어도 이를 무시할 수 있다.")
    @Test
    void test6() throws JsonProcessingException {
        String jsonString = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Car car = objectMapper.readValue(jsonString, Car.class);
        assertThat(car).isNotNull();

        JsonNode jsonNodeRoot = objectMapper.readTree(jsonString);
        JsonNode jsonNodeYear = jsonNodeRoot.get("year");
        String year = jsonNodeYear.asText();
        assertThat(year).isEqualTo("1970");
    }

    private static class CustomCarSerializer extends StdSerializer<Car> {
        public CustomCarSerializer() {
            this(null);
        }

        protected CustomCarSerializer(Class<Car> t) {
            super(t);
        }

        @Override
        public void serialize(Car car, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("car_brand", car.type());
            jsonGenerator.writeEndObject();
        }
    }

    @DisplayName("Serializer 를 커스터마이징 할 수 있다.")
    @Test
    void test7() throws JsonProcessingException {
        SimpleModule module = new SimpleModule("CustomCarSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(Car.class, new CustomCarSerializer());
        objectMapper.registerModule(module);

        Car car = new Car("yellow", "renault");
        String carJson = objectMapper.writeValueAsString(car);

        assertThat(carJson).isEqualTo("{\"car_brand\":\"renault\"}");
    }

    private static class CustomCarDeserializer extends StdDeserializer<Car> {
        public CustomCarDeserializer() {
            this(null);
        }

        public CustomCarDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Car deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
            ObjectCodec codec = parser.getCodec();
            JsonNode node = codec.readTree(parser);

            // try catch block
            String color = node.get("color").asText();
            String type = node.get("type").asText();
            return new Car(color + " white", "super " + type);
        }
    }

    @DisplayName("Deserializer를 커스터마이징 할 수 있다.")
    @Test
    void test8() throws JsonProcessingException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";

        SimpleModule module = new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Car.class, new CustomCarDeserializer());

        objectMapper.registerModule(module);
        Car car = objectMapper.readValue(json, Car.class);

        assertThat(car.color()).isEqualTo("Black white");
        assertThat(car.type()).isEqualTo("super BMW");
    }

    private record Purchase(Car car, Date purchasedAt) {}

    @DisplayName("날짜 형식을 처리할 수 있다.")
    @Test
    void test9() throws JsonProcessingException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        objectMapper.setDateFormat(simpleDateFormat);

        Purchase purchase = new Purchase(new Car("blue", "supercar"), new Date(1));
        String json = objectMapper.writeValueAsString(purchase);

        assertThat(json).isEqualTo("{\"car\":{\"color\":\"blue\",\"type\":\"supercar\"},\"purchasedAt\":\"1970-01-01 09:00 오전 KST\"}");
    }
}
