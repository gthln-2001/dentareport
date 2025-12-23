package de.dentareport.gui.views.start;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class StartPresenterTest {

    private UiController uiController;
    private StartPresenter startPresenter;

    @BeforeEach
    public void setUp() {
        uiController = mock(UiController.class);
        startPresenter = new StartPresenter(uiController);
    }

    @Test
    void onOpenReport_showsReportView() {
        startPresenter.onOpenReport();

        verify(uiController).showView(ViewId.REPORT);
        verifyNoMoreInteractions(uiController);
    }

    @Test
    void onExitRequested_confirmsExit() {
        startPresenter.onExitRequested();

        verify(uiController).confirmExit();
        verifyNoMoreInteractions(uiController);
    }
}
