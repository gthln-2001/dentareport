package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableMap;
import de.dentareport.evaluations.meta.dependencies.AvailableDependencies;
import de.dentareport.evaluations.meta.events.AvailableEvents;
import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import de.dentareport.utils.Keys;
import de.dentareport.utils.MiniCache;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Map;

public abstract class ContentPaneEvaluationProbabilities extends ContentPane {

    private final AvailableEvents availableEvents;
    private final AvailableDependencies availableDependencies;
    private final Map<String, String> options;
    private Gui gui;

    public ContentPaneEvaluationProbabilities(Gui gui, Map<String, String> options) {
        this.gui = gui;
        this.availableEvents = new AvailableEvents(options.get("evaluationId"));
        this.availableDependencies = new AvailableDependencies(options.get("evaluationId"));
        this.options = options;
    }

    protected abstract String guiText();

    protected abstract String keyProbabilitiesDisplayView();

    protected abstract String parentPane();

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
                      .left(buttonBack())
                      .right(buttonQuit())
                      .create();
    }

    private HBox heading() {
        HBox box = new HBox();
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                Element.label()
                       .text(guiText())
                       .styleClass("label-big")
                       .create());
        return box;
    }

    private VBox center() {
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                Element.label()
                       .text(Keys.GUI_TEXT_PROBABILITIES)
                       .styleClass("label-big")
                       .create(),
                radioButtons(),
                Element.button()
                       .text(Keys.GUI_TEXT_DISPLAY)
                       .action(e -> displayProbabilities())
                       .create()
        );
        return box;
    }

    private HBox radioButtons() {
        HBox box = new HBox(40);
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(buttonColumnEvents(), buttonColumnDependencies());
        return box;
    }

    private VBox buttonColumnEvents() {
        ToggleGroup toggleGroup = toggleGroupEvents();
        String selectedEvent = selectedEvent();
        VBox box = new VBox(16);
        box.setId("buttons-events");
        box.getChildren().add(
                Element.label()
                       .text(String.format("%s:", Keys.GUI_TEXT_SELECT_EVENT))
                       .styleClass("label-medium")
                       .create());
        availableEvents.events()
                       .forEach(e -> box.getChildren().add(
                               Element.radioButton()
                                      .text(e.name(), selectedEvent)
                                      .group(toggleGroup)
                                      .create()));
        return box;
    }

    private ToggleGroup toggleGroupEvents() {
        ToggleGroup eventGroup = new ToggleGroup();
        eventGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
                                                                cacheSelectedEvent(newValue)
        );
        return eventGroup;
    }

    private void cacheSelectedEvent(Toggle newValue) {
        cacheNewValue("selected_event", newValue);
    }

    private void cacheNewValue(String key, Toggle newValue) {
        MiniCache.put(key, ((RadioButton) newValue.getToggleGroup().getSelectedToggle()).getText());
    }

    private String selectedEvent() {
        return MiniCache.getOrDefault("selected_event", Keys.GUI_TEXT_TOOTHLOSS);
    }

    private VBox buttonColumnDependencies() {
        ToggleGroup toggleGroup = toggleGroupDependencies();
        String selectedDependency = selectedDependency();
        VBox box = new VBox(16);
        box.setId("buttons-dependencies");
        box.getChildren().add(Element.label()
                                     .text(String.format("%s:", Keys.GUI_TEXT_SELECT_DEPENDENCY))
                                     .styleClass("label-medium")
                                     .create());
        availableDependencies.dependencies()
                             .forEach(e -> box.getChildren()
                                              .add(Element.radioButton()
                                                          .text(e.name(), selectedDependency)
                                                          .group(toggleGroup)
                                                          .create()));
        return box;
    }

    private ToggleGroup toggleGroupDependencies() {
        ToggleGroup dependencyGroup = new ToggleGroup();
        dependencyGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
                                                                     cacheSelectedDependency(newValue)
        );
        return dependencyGroup;
    }

    private void cacheSelectedDependency(Toggle newValue) {
        cacheNewValue("selected_dependency", newValue);
    }

    private String selectedDependency() {
        return MiniCache.getOrDefault("selected_dependency", Keys.GUI_TEXT_NO_DEPENDENCY);
    }

    private void displayProbabilities() {
        gui.setContentPane(ImmutableMap.of(
                "evaluationId", options.get("evaluationId"),
                "view", keyProbabilitiesDisplayView(),
                "event", selectedEvent(),
                "dependency", selectedDependency()
        ));
    }

    private Button buttonQuit() {
        return Element.button()
                      .text(Keys.GUI_TEXT_QUIT)
                      .action(e -> gui.closeProgram())
                      .create();
    }

    private Button buttonBack() {
        return Element.button()
                      .text(Keys.GUI_TEXT_BACK)
                      .action(e -> gui.setContentPane(parentPane()))
                      .create();
    }
}
