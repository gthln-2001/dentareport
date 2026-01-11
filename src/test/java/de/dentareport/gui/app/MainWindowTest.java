package de.dentareport.gui.app;

import de.dentareport.gui.navigation.ViewFactory;
import de.dentareport.gui.navigation.ViewId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

// TODO: Check test, this is just a first draft
class MainWindowTest {

    private ViewFactory viewFactory;
    private JFrame jFrame;
    private JPanel jPanel;

    @BeforeEach
    public void setUp() throws Exception {
        viewFactory = mock(ViewFactory.class);
        jFrame = mock(JFrame.class);
        jPanel = new JPanel();
        when(jFrame.getContentPane()).thenReturn(jPanel);
    }

    @Test
    void showView_replacesContentWithViewFromFactory() {
        JComponent fakeView = new JLabel("start");
        when(viewFactory.create(eq(ViewId.START), any())).thenReturn(fakeView);

        MainWindow window = new MainWindow(viewFactory, jFrame);
        window.showView(ViewId.START);

        JPanel content = window.getContentPanel();

        assertThat(content.getComponentCount()).isEqualTo(1);
        assertThat(content.getComponent(0)).isSameAs(fakeView);
    }


    @Test
    void setWindowTitle_setsFrameTitle() throws Exception {
        MainWindow window = new MainWindow(viewFactory, jFrame);

        window.setWindowTitle("Dentareport");

        verify(jFrame).setTitle("Dentareport");
    }
}
