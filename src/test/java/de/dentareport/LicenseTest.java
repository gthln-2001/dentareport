package de.dentareport;

import de.dentareport.utils.MiniCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenseTest {

    private License license;

    @BeforeEach
    public void setUp() {
        this.license = new License();
        MiniCache.clear();
    }

    @Test
    public void it_checks_if_program_runs_in_demo_mode() {
        assertThat(license.isDemo()).isTrue();
        assertThat(license.isDemo()).isTrue(); // check if default value is set at first call

        MiniCache.put("no_demo", "true");
        assertThat(license.isDemo()).isFalse();

        MiniCache.put("no_demo", "false");
        assertThat(license.isDemo()).isTrue();

        MiniCache.put("no_demo", "something");
        assertThat(license.isDemo()).isTrue();
    }
}