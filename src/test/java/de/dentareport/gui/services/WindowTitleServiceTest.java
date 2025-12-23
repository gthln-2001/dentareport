package de.dentareport.gui.services;

import de.dentareport.License;
import de.dentareport.utils.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WindowTitleServiceTest {

    private License license;

    @BeforeEach
    public void setUp() {
        license = mock(License.class);
    }

    @Test
    void returns_demo_title_when_license_is_demo() {
        when(license.isDemo()).thenReturn(true);

        WindowTitleService windowTitleService = new WindowTitleService(license);
        String title = windowTitleService.title();

        assertThat(title).isEqualTo(Keys.GUI_WINDOW_TITLE_MAIN + " - " + Keys.GUI_TEXT_DEMO_VERSION);
    }

    @Test
    void returns_normal_title_when_license_is_not_demo() {
        when(license.isDemo()).thenReturn(false);

        WindowTitleService windowTitleService = new WindowTitleService(license);
        String title = windowTitleService.title();

        assertThat(title).isEqualTo(Keys.GUI_WINDOW_TITLE_MAIN);
    }
}
