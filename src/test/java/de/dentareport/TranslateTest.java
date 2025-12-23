package de.dentareport;

import de.dentareport.utils.Keys;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TranslateTest {

    @Test
    public void it_translates_string() {
        Translate translate = new Translate();

        assertThat(translate.translate(Keys.CROWN)).isEqualTo("Krone");
    }

    @Test
    public void it_returns_unchanged_string_if_translation_does_not_exist() {
        Translate translate = new Translate();

        assertThat(translate.translate("some_string_that_has_no_translation_whatsoever"))
                .isEqualTo("some_string_that_has_no_translation_whatsoever");
    }
}