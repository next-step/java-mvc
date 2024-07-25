package com.interface21.core.util;

import static org.assertj.core.api.Assertions.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("StreamUtils 테스트")
class StreamUtilsTest {

    @Test
    @DisplayName("두 Set의 모든 원소들을 합친다")
    public void addAllTest() {

        final var aSet = new HashSet<>();
        aSet.add("A");

        final var bSet = new HashSet<>();
        bSet.add("B");

        assertThat(StreamUtils.addAll(aSet, bSet)).containsExactlyInAnyOrder("A", "B");
    }

    @Test
    @DisplayName("addAll()은 null 세이프하지 않다")
    public void addAllFailTestWhenParameterIsNull() {

        final var bSet = new HashSet<>();
        bSet.add("B");

        assertThatThrownBy(() -> StreamUtils.addAll(null, bSet))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("두 Map의 모든 Entry를 합친다")
    public void putAllTest() {

        final var aMap = new HashMap<>();
        aMap.put("A", 1);

        final var bMap = new HashMap<>();
        bMap.put("B", 2);

        assertThat(StreamUtils.pullAll(aMap, bMap)).containsEntry("A", 1).containsEntry("B", 2);
    }

    @Test
    @DisplayName("pullAll()은 null 세이프하지 않다")
    public void pullAllFailWhenParameterIsNull() {

        final var bMap = new HashMap<>();
        bMap.put("B", 2);

        assertThatThrownBy(() -> StreamUtils.pullAll(null, bMap))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Map.Entry value의 List 원소를 펼친다")
    public void flattenValuesTest() {

        final Map.Entry<String, List<String>> entry = new SimpleEntry<>("KEY", List.of("a", "b"));

        assertThat(StreamUtils.flattenValues(entry))
                .containsExactly(new SimpleEntry<>("KEY", "a"), new SimpleEntry<>("KEY", "b"));
    }

    @Test
    @DisplayName("List원소를 펼처서 Map.entry를 반한다")
    public void flattenValues2Test() {

        assertThat(StreamUtils.flattenValues("KEY", List.of("a", "b")))
                .containsExactly(new SimpleEntry<>("KEY", "a"), new SimpleEntry<>("KEY", "b"));
    }

    @Test
    @DisplayName("flattenValues()는 null 세이프하지 않다")
    public void flattenValuesFailWhenParameterIsNull() {

        assertThatThrownBy(() -> StreamUtils.flattenValues(null))
                .isInstanceOf(NullPointerException.class);
    }
}
