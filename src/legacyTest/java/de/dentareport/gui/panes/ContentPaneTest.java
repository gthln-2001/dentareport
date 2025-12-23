package de.dentareport.gui.panes;

import de.dentareport.gui.elements.Utils;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContentPaneTest {

    private Foo foo;
    @Mocked
    Pane mockPaneCenter;
    @Mocked
    Pane mockPaneMenu;

    @BeforeEach
    public void setUp() throws Exception {
        foo = new Foo();
    }

    @Test
    public void it_sets_content_and_menu_pane(@Mocked BorderPane mockBorderPane) {
        foo.pane();

        new Verifications() {{
            mockBorderPane.setCenter(mockPaneCenter);
            mockBorderPane.setBottom(mockPaneMenu);
        }};
    }

    @Test
    public void it_enables_all_menu_buttons(@Mocked Utils mockUtils) {
        foo.enableMenuButtons();

        new Verifications() {{
            Utils.enableButtons(mockPaneMenu);
        }};
    }

    @Test
    public void it_disables_all_menu_buttons(@Mocked Utils mockUtils) {
        foo.disableMenuButtons();

        new Verifications() {{
            Utils.disableButtons(mockPaneMenu);
        }};
    }

    class Foo extends ContentPane {

        @Override
        protected Pane content() {
            return mockPaneCenter;
        }

        @Override
        protected Pane menu() {
            return mockPaneMenu;
        }
    }
}