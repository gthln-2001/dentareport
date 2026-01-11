package de.dentareport.gui.views.start;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.testutil.SwingTestUtils.clickButtonByName;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

// TODO: Check test, this is just a first draft
class StartViewTest {

    private StartPresenter startPresenter;
    private StartView startView;

    @BeforeEach
    public void setUp() throws Exception {
        startPresenter = mock(StartPresenter.class);
        startView = new StartView(startPresenter);
    }

//    @Test
//    void clicking_import_data_calls_presenter() throws Exception {
//        clickButtonByName(startView, "import_data");
//
//        verify(startPresenter).onImportData();
//    }
//
//    @Test
//    void clicking_exit_calls_presenter() throws Exception {
//        clickButtonByName(startView, "exit");
//
//        verify(startPresenter).onExitRequested();
//    }
}
