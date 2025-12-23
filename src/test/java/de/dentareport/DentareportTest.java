package de.dentareport;

import de.dentareport.gui.app.ApplicationWindow;
import de.dentareport.gui.app.LookAndFeelInitializer;
import de.dentareport.gui.services.WindowTitleService;
import de.dentareport.utils.PreStarter;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DentareportTest {

    public static final String TEST_TITLE = "Some title";

    @Test
    void it_initializes_and_shows_the_main_window() {
        PreStarter preStarter = mock(PreStarter.class);
        LookAndFeelInitializer lookAndFeelInitializer = mock(LookAndFeelInitializer.class);
        ApplicationWindow applicationWindow = mock(ApplicationWindow.class);
        WindowTitleService windowTitleService = mock(WindowTitleService.class);

        when(windowTitleService.title()).thenReturn(TEST_TITLE);

        Dentareport dentareport = new Dentareport(
                preStarter,
                lookAndFeelInitializer,
                applicationWindow,
                windowTitleService
        );

        dentareport.run();

        verify(preStarter).runPreStartTasks();
        verify(lookAndFeelInitializer).setup();
        verify(applicationWindow).setWindowTitle(TEST_TITLE);
        verify(applicationWindow).show();
    }
}
