package de.dentareport.gui.elements;

import de.dentareport.gui.BaseFxElementTest;
import de.dentareport.utils.Keys;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuElementTest extends BaseFxElementTest {

    private MenuElement menuElement;

    @BeforeEach
    public void setUp() {
        menuElement = new MenuElement();
    }

    @Test
    public void it_creates_menu() {
        BorderPane menu = menuElement.create();

        assertThat(menu).isInstanceOf(BorderPane.class);
    }

    @Test
    public void it_sets_id_for_menu() {
        BorderPane menu = menuElement.create();

        assertThat(menu.getId()).isEqualTo(Keys.GUI_ID_MENU);
    }

    @Test
    public void it_sets_left_node(@Mocked BorderPane mockBorderPane) {
        Node leftNode = new Pane();

        menuElement.left(leftNode).create();

        new Verifications() {{
            mockBorderPane.setLeft(leftNode);
        }};
    }

    @Test
    public void it_sets_right_node(@Mocked BorderPane mockBorderPane) {
        Node rightNode = new Pane();

        menuElement.right(rightNode).create();

        new Verifications() {{
            mockBorderPane.setRight(rightNode);
        }};
    }
}