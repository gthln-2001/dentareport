package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableList;
import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import de.dentareport.gui.tables.Averages;
import de.dentareport.gui.tables.CountAndDistribution;
import de.dentareport.utils.Keys;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.Map;

public abstract class ContentPaneEvaluationGeneral extends ContentPane {

    private final Averages tableDataAverages;
    private final CountAndDistribution tableDataCountAndDistribution;
    private final Gui gui;

    public ContentPaneEvaluationGeneral(Gui gui, Map<String, String> options) {
        this.gui = gui;
        this.tableDataAverages = new Averages(options);
        this.tableDataCountAndDistribution = new CountAndDistribution(options);
    }

    protected abstract String centerLabel();

    protected abstract String parentPane();

    @Override
    protected Pane content() {
        BorderPane pane = new BorderPane();
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

    private VBox center() {
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                Element.label()
                        .text(centerLabel())
                        .styleClass("label-medium")
                        .alignment(TextAlignment.CENTER)
                        .create(),
                tableAverages(),
                tableCountAndDistribution()
        );
        return box;
    }

    private TableView<?> tableAverages() {
        return Element.table()
                .id("table-averages")
                .data(tableDataAverages.data())
                .columns(ImmutableList.of(
                        Keys.GUI_TEXT_BLANK,
                        Keys.GUI_TEXT_BLANK,
                        Keys.GUI_TEXT_AVERAGE,
                        Keys.GUI_TEXT_MEDIAN,
                        Keys.GUI_TEXT_MINIMUM,
                        Keys.GUI_TEXT_MAXIMUM))
                .columnWidth(ImmutableList.of(250))
                .create();
    }

    private TableView<?> tableCountAndDistribution() {
        return Element.table()
                .id("table-count-and-distribution")
                .data(tableDataCountAndDistribution.data())
                .columns(ImmutableList.of(
                        Keys.GUI_TEXT_ITEM,
                        Keys.GUI_TEXT_BLANK))
                .height(350)
                .create();
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
