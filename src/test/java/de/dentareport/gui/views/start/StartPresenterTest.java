package de.dentareport.gui.views.start;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

// TODO: Check test, this is just a first draft
class StartPresenterTest {

    private UiController uiController;
    private StartPresenter startPresenter;

    @BeforeEach
    public void setUp() {
        uiController = mock(UiController.class);
        startPresenter = new StartPresenter(uiController);
    }

    @Test
    void onImportDataView() {
        startPresenter.onImportData();

        verify(uiController).showView(ViewId.IMPORT_DATA);
        verifyNoMoreInteractions(uiController);
    }

    @Test
    void onExitRequested_confirmsExit() {
        startPresenter.onExitRequested();

        verify(uiController).confirmExit();
        verifyNoMoreInteractions(uiController);
    }
}
