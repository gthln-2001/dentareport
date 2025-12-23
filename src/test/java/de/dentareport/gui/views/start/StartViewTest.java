package de.dentareport.gui.views.start;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.testutil.SwingTestUtils.clickButtonByName;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class StartViewTest {

    private StartPresenter startPresenter;
    private StartView startView;

    @BeforeEach
    public void setUp() throws Exception {
        startPresenter = mock(StartPresenter.class);
        startView = new StartView(startPresenter);
    }

    @Test
    void clicking_open_report_calls_presenter() {
        clickButtonByName(startView, "open_report");

        verify(startPresenter).onOpenReport();
    }

    @Test
    void clicking_exit_calls_presenter() {
        clickButtonByName(startView, "exit");

        verify(startPresenter).onExitRequested();
    }
}
