package de.dentareport.models;

import com.google.common.collect.ImmutableList;
import de.dentareport.utils.Keys;

import java.util.List;

import static de.dentareport.utils.Keys.NO_DATA;

public interface Evidence01Interface extends EventInterface {

    default String tooth() {
        return Keys.NO_DATA;
    }

    default String handler() {
        return Keys.NO_DATA;
    }

    default String insurance() {
        return Keys.NO_DATA;
    }

    default String source() {
        return Keys.NO_DATA;
    }

    default List<String> surfaces() {
        return ImmutableList.of();
    }

    default String comment() {
        return NO_DATA;
    }
}
