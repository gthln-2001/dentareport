package de.dentareport.evaluations.meta.averages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemsTest {

    private Items items;

    @BeforeEach
    public void setUp() throws Exception {
        items = new Items();
    }

    @Test
    public void it_gets_items_that_are_to_be_evaluated_per_patient() {
        for (Map<String, String> item : items.perPatient()) {
            assertThat(item).containsKeys("column", "unit", "name");
        }
    }

    @Test
    public void it_gets_items_that_are_to_be_evaluated_per_case() {
        for (Map<String, String> item : items.perCase()) {
            assertThat(item).containsKeys("column", "unit", "name");
        }
    }
}