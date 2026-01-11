package de.dentareport.gui.navigation;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.views.start.StartView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static de.dentareport.testutil.SwingTestUtils.callOnEdt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ViewFactoryTest {

    private ViewFactory viewFactory;
    private UiController uiController;

    @BeforeEach
    public void setUp() {
        viewFactory = new ViewFactory();
        uiController = mock(UiController.class);
    }

    @Test
    void creates_start_view() throws Exception {
        JComponent view = callOnEdt(() -> viewFactory.create(ViewId.START, uiController));

        assertThat(view).isInstanceOf(StartView.class);
    }

    @Test
    void creates_report_placeholder() throws Exception {
        JComponent view = callOnEdt(() -> viewFactory.create(ViewId.REPORT, uiController));

        assertThat(view).isInstanceOf(JLabel.class);
        assertThat(((JLabel) view).getText()).contains("Report");
    }
}
