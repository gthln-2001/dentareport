package de.dentareport.utils.map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.*;

import static de.dentareport.utils.map.MapUtils.toLinkedMap;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class MapUtilsTest {

    @Test
    public void it_sorts_a_map_by_key() {
        Map<String, Integer> toSort = new HashMap<>();
        toSort.put("foo", 3);
        toSort.put("bar", 1);
        toSort.put("biz", 2);
        toSort.put("baz", 4);

        List<String> sorted = new ArrayList<>(MapUtils.sortByKey(toSort).keySet());
        assertThat(sorted.size()).isEqualTo(4);
        assertThat(sorted.get(0)).isEqualTo("bar");
        assertThat(sorted.get(1)).isEqualTo("baz");
        assertThat(sorted.get(2)).isEqualTo("biz");
        assertThat(sorted.get(3)).isEqualTo("foo");
    }

    @Test
    public void it_sorts_a_map_by_value() {
        Map<String, Integer> toSort = new HashMap<>();
        toSort.put("foo", 3);
        toSort.put("bar", 1);
        toSort.put("biz", 2);
        toSort.put("baz", 4);

        List<String> sorted = new ArrayList<>(MapUtils.sortByValue(toSort).keySet());
        assertThat(sorted.size()).isEqualTo(4);
        assertThat(sorted.get(0)).isEqualTo("bar");
        assertThat(sorted.get(1)).isEqualTo("biz");
        assertThat(sorted.get(2)).isEqualTo("foo");
        assertThat(sorted.get(3)).isEqualTo("baz");
    }

    @Test
    public void it_collects_a_stream_to_a_linked_hash_map() {
        List<String> values = ImmutableList.of("a", "b", "c");

        LinkedHashMap<String, Map<String, String>> ret = values.stream()
                .collect(toLinkedMap(v -> v + "x", v -> ImmutableMap.of(v, v)));

        assertThat(ret.size()).isEqualTo(3);

        Iterator<Map.Entry<String, Map<String, String>>> keys = ret.entrySet().iterator();
        assertThat(keys.next().getKey()).isEqualTo("ax");
        assertThat(keys.next().getKey()).isEqualTo("bx");
        assertThat(keys.next().getKey()).isEqualTo("cx");
    }
}