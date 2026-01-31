package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableList;
import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import de.dentareport.gui.tables.GeneralPatientInformationAverages;
import de.dentareport.gui.tables.GeneralPatientInformationGroupedByAge;
import de.dentareport.utils.Keys;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Map;

// TODO: TEST?
public class GeneralPatientInformation extends ContentPane {

    private Gui gui;
    private final GeneralPatientInformationAverages generalPatientInformationAverages;
    private final GeneralPatientInformationGroupedByAge generalPatientInformationGroupedByAge;

    public GeneralPatientInformation(Gui gui,
                                     Map<String, String> options) {
        this.gui = gui;
        this.generalPatientInformationAverages = new GeneralPatientInformationAverages();
        this.generalPatientInformationGroupedByAge = new GeneralPatientInformationGroupedByAge();
    }

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
                        .text(Keys.GUI_TEXT_GENERAL_PATIENT_INFORMATION)
                        .styleClass("label-medium")
                        .create(),
                tableAverages(),
                tableGroupedByAge()
        );
        return box;
    }

    private TableView<?> tableAverages() {
        return Element.table()
                .id("table-averages")
                .data(generalPatientInformationAverages.data())
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

    private TableView<?> tableGroupedByAge() {
        return Element.table()
                .id("table-grouped-by-age")
                .data(generalPatientInformationGroupedByAge.data())
                .columns(columnsGroupedByAge())
                .columnWidth(columnWidthGroupedByAge())
                .create();
    }

    private ImmutableList<Integer> columnWidthGroupedByAge() {
        ImmutableList.Builder<Integer> widths = ImmutableList.<Integer>builder()
                .add(250)
                .add(50);

        generalPatientInformationGroupedByAge.groups().forEach(group -> widths.add(50));

        return widths.build();
    }

    private ImmutableList<String> columnsGroupedByAge() {
        ImmutableList.Builder<String> columns = ImmutableList.<String>builder()
                .add(Keys.GUI_TEXT_BLANK)
                .add(Keys.GUI_TEXT_BLANK);

        generalPatientInformationGroupedByAge.groups().forEach(columns::add);

        return columns.build();
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
                .action(e -> gui.setContentPane(Keys.GUI_VIEW_EVALUATION))
                .create();
    }
}
