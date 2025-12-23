package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import de.dentareport.gui.tables.AnnualFailureRate;
import de.dentareport.gui.tables.GroupsSizes;
import de.dentareport.repositories.KaplanMeierRepository;
import de.dentareport.utils.Keys;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.Objects;

public abstract class ContentPaneEvaluationProbabilitiesDisplay extends ContentPane {

    private final Map<String, String> options;
    private final AnnualFailureRate tableDataAnnualFailureRate;
    private final KaplanMeierRepository kaplanMeierRepository;
    private Gui gui;
    private GroupsSizes tableDataGroupSizes;

    public ContentPaneEvaluationProbabilitiesDisplay(Gui gui, Map<String, String> options) {
        this.gui = gui;
        this.options = options;
        this.tableDataGroupSizes = new GroupsSizes();
        this.tableDataAnnualFailureRate = new AnnualFailureRate();
        this.kaplanMeierRepository = new KaplanMeierRepository();
    }

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
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
                chartKaplanMeier(),
                tableGroupSizes(),
                tableAfr()
        );
        return box;
    }

    private LineChart<Number, Number> chartKaplanMeier() {
        return Element.lineChart()
                      .id("chart-kaplan-meier")
                      .lines(kaplanMeierRepository.lines(options))
                      .translateLegend()
                      .title(chartTitle())
                      .xLabel(Keys.GUI_TEXT_YEARS)
                      .yLabel(Keys.GUI_TEXT_PROBABILITIES)
                      .autoMinY()
                      .create();
    }

    private String chartTitle() {
        String title = String.format("%s %s %s",
                                     Keys.GUI_TEXT_FILLINGS,
                                     Keys.GUI_TEXT_UNDER_EVENT,
                                     options.get("event"));
        if (isGrouped()) {
            title += String.format("\n%s %s",
                                   Keys.GUI_TEXT_GROUPED_BY,
                                   options.get("dependency"));
        }
        return title;
    }

    private boolean isGrouped() {
        return !Objects.equals(options.get("dependency"), Keys.GUI_TEXT_NO_DEPENDENCY);
    }

    private TableView<?> tableGroupSizes() {
        return Element.table()
                      .id("table-group-sizes")
                      .data(tableDataGroupSizes.data(options))
                      .columns(ImmutableList.of(
                              Keys.GUI_TEXT_ITEM,
                              Keys.GUI_TEXT_COUNT))
                      .create();
    }

    private TableView<?> tableAfr() {
        return Element.table()
                      .id("table-afr")
                      .data(tableDataAnnualFailureRate.data(options))
                      .columns(ImmutableList.of(
                              Keys.GUI_TEXT_ITEM,
                              Keys.GUI_TEXT_AFR_5_YEARS,
                              Keys.GUI_TEXT_AFR_10_YEARS))
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
                      .action(e -> gui.setContentPane(ImmutableMap.of("view",
                                                                      parentPane(),
                                                                      "evaluationId",
                                                                      Keys.EVALUATION_TYPE_FILLING)))
                      .create();
    }
}
