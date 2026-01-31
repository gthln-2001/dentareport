package de.dentareport.gui.tables;

import de.dentareport.Translate;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class TableTest {

    @Mocked
    Translate mockTranslate;

    @Test
    public void it_translates_string() {
        new Expectations() {{
            mockTranslate.translate("foobar");
            result = "bizbaz";
        }};

        assertThat(new Foo().translate("foobar")).isEqualTo("bizbaz");
    }

    private class Foo extends Table {
    }
}