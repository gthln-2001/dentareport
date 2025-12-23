package de.dentareport.gui.panes;

import de.dentareport.evaluations.Evaluation7;
import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import de.dentareport.utils.Keys;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Map;

public class Evaluation extends ContentPane {

    private Gui gui;
    private int buttonWidth = 250;

    public Evaluation(Gui gui,
                      Map<String, String> options) {
        this.gui = gui;
    }

    @Override
    protected Pane content() {
        BorderPane pane = new BorderPane();
        pane.setTop(heading());
        pane.setCenter(center());
        return pane;
    }

    @Override
    protected Pane menu() {
        return Element.menu()
                      .left(
                              Element.button()
                                     .text(Keys.GUI_TEXT_BACK)
                                     .action(e -> gui.setContentPane(Keys.GUI_VIEW_START))
                                     .create())
                      .right(
                              Element.button()
                                     .text(Keys.GUI_TEXT_QUIT)
                                     .action(e -> gui.closeProgram())
                                     .create())
                      .create();
    }

    private HBox heading() {
        HBox heading = new HBox();
        heading.setPadding(new Insets(20, 20, 20, 20));
        heading.setAlignment(Pos.CENTER);
        heading.getChildren().addAll(
                Element.label()
                       .text(Keys.GUI_TEXT_EVALUATIONS)
                       .styleClass("label-big")
                       .create());
        return heading;
    }

    private VBox center() {
        VBox box = new VBox(30);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                Element.label()
                       .text(Keys.GUI_TEXT_CHOOSE_EVALUATION)
                       .styleClass("label-medium")
                       .create(),
                buttons(),
                Element.button()
                       .text(Keys.GUI_TEXT_GENERAL_PATIENT_INFORMATION)
                       .action(e -> gui.setContentPane(Keys.GUI_VIEW_GENERAL_PATIENT_INFORMATION))
                       .create());
        return box;
    }

    private HBox buttons() {
        HBox box = new HBox(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                buttonColumnLeft(),
                buttonColumnRight()
        );
        return box;
    }

    private VBox buttonColumnRight() {
        VBox box = new VBox(20);
        box.getChildren().addAll(
                Element.button()
                       .text(Keys.GUI_TEXT_SEALINGS)
                       .action(e -> Element.notAvailableInDemoVersion())
                       .width(buttonWidth)
                       .styleClass("button-function-not-available")
                       .create(),
                Element.button()
                       .text(Keys.GUI_TEXT_WURZELSTIFTE)
                       .action(e -> Element.notAvailableInDemoVersion())
                       .width(buttonWidth)
                       .styleClass("button-function-not-available")
                       .create(),
                Element.button()
                       .text(Keys.GUI_TEXT_PREVENTION_MEASURES)
                       .action(e -> Element.notAvailableInDemoVersion())
                       .width(buttonWidth)
                       .styleClass("button-function-not-available")
                       .create(),
                Element.button()
                       .text(Keys.GUI_TEXT_INLAYS)
                       .action(e -> Element.notAvailableInDemoVersion())
                       .width(buttonWidth)
                       .styleClass("button-function-not-available")
                       .create(),
                Element.button()
                       .text(Keys.GUI_TEXT_SURGERY)
                       .action(e -> Element.notAvailableInDemoVersion())
                       .width(buttonWidth)
                       .styleClass("button-function-not-available")
                       .create()
        );
        return box;
    }

    private VBox buttonColumnLeft() {
        VBox box = new VBox(20);
        box.getChildren().addAll(
                Element.button()
                       .text(Keys.GUI_TEXT_FILLINGS)
                       .action(e -> gui.setContentPane(Keys.GUI_VIEW_EVALUATION_FILLING))
                       .width(buttonWidth)
                       .create(),
                Element.button()
                       .text(Keys.GUI_TEXT_ENDODONTICS)
                       .action(e -> Element.notAvailableInDemoVersion())
                       .width(buttonWidth)
                       .styleClass("button-function-not-available")
                       .create(),
                Element.button()
                       .text(Keys.GUI_TEXT_CROWNS)
                       .action(e -> Element.notAvailableInDemoVersion())
                       .width(buttonWidth)
                       .styleClass("button-function-not-available")
                       .create(),
                Element.button()
                       .text(Keys.GUI_TEXT_BRIDGES)
                       .action(e -> Element.notAvailableInDemoVersion())
                       .width(buttonWidth)
                       .styleClass("button-function-not-available")
                       .create(),
                Element.button()
                       .text(Keys.GUI_TEXT_TELESCOPIC_CROWNS)
                       .action(e -> gui.setContentPane(Keys.GUI_VIEW_EVALUATION_TELESCOPIC_CROWN))
                       .width(buttonWidth)
                       .create(),
                Element.button()
                       .text(Keys.GUI_TEXT_IMPLANTS)
                       .action(e -> Element.notAvailableInDemoVersion())
                       .width(buttonWidth)
                       .styleClass("button-function-not-available")
                       .create()
        );
        return box;
    }
}
