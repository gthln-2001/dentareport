package de.dentareport.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// TODO: TEST?
public class MiniCacheTest {

    @BeforeEach
    public void setUp() throws Exception {
        MiniCache.clear();
    }

    @Test
    public void it_stores_and_retrieves_data() {
        MiniCache.put("foo", "bar");
        MiniCache.put("biz", "baz");

        assertThat(MiniCache.get("foo")).isEqualTo("bar");
        assertThat(MiniCache.get("biz")).isEqualTo("baz");
    }

    @Test
    public void it_throws_exception_when_trying_to_get_value_for_key_that_does_not_exist() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> MiniCache.get("foo")
        );
    }

    @Test
    public void it_gets_value_or_sets_given_default_if_value_does_not_exist() {
        assertThat(MiniCache.getOrDefault("foo", "bar")).isEqualTo("bar");
        assertThat(MiniCache.getOrDefault("foo", "biz")).isEqualTo("bar");
    }

    @Test
    public void it_clears_all_values_from_cache() {
        MiniCache.put("foo", "bar");
        MiniCache.put("biz", "baz");

        MiniCache.clear();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> MiniCache.get("foo")
        );
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> MiniCache.get("biz")
        );
    }
}