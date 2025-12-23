package de.dentareport.imports.dampsoft.convert;

import de.dentareport.imports.dampsoft.dampsoft_files.PatkuerzDbf;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TokensConvertTest {

    @Test
    public void it_converts_tokens(@Mocked PatkuerzDbf patkuerzDbf) {
        new Expectations() {{
            PatkuerzDbf.token(0);
            result = "A";
            PatkuerzDbf.token(2);
            result = "C";
        }};

        assertThat(TokensConvert.convert("J JNXZ")).isEqualTo("A,C");
    }
}