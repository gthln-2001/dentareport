package de.dentareport.gui.elements;

import de.dentareport.utils.Keys;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

// TODO: TEST?
public class MenuElement {

    private Node left;
    private Node right;

    public BorderPane create() {
        BorderPane pane = new BorderPane();
        pane.setId(Keys.GUI_ID_MENU);
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.setLeft(left);
        pane.setRight(right);
        return pane;
    }

    public MenuElement left(Node value) {
        left = value;
        return this;
    }

    public MenuElement right(Node value) {
        right = value;
        return this;
    }
}
