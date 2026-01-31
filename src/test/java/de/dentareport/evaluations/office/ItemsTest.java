package de.dentareport.evaluations.office;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class ItemsTest {

    @Test
    public void it_has_list_of_items() {
        Items items = new Items();

        for (Item item: items.items()) {
            assertThat(item).isInstanceOf(Item.class);
        }
    }
}