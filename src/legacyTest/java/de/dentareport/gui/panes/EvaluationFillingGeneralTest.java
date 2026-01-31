package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableMap;
import de.dentareport.gui.BaseFxElementTest;
import de.dentareport.gui.Gui;
import de.dentareport.gui.tables.Averages;
import de.dentareport.gui.tables.CountAndDistribution;
import de.dentareport.utils.Keys;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class EvaluationFillingGeneralTest extends BaseFxElementTest {

    @Mocked
    Gui mockGui;
    @Mocked
    CountAndDistribution mockTableDataCountAndDistribution;
    @Mocked
    Averages mockTableDataAverages;

    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        Assertions.assertThat(true).isTrue();
    }

    // TODO: Fix test
//    @Test
//    public void it_has_table_for_averages() {
//        TableView<?> table = find("#table-averages");
//        assertThat(table).isNotNull();
//
//        new Verifications() {{
//            mockTableDataAverages.data();
//            times = 1;
//        }};
//    }

    // TODO: Fix test
//    @Test
//    public void it_has_table_for_count_and_distribution() {
//        TableView<?> table = find("#table-count-and-distribution");
//        assertThat(table).isNotNull();
//
//        new Verifications() {{
//            mockTableDataCountAndDistribution.data();
//            times = 1;
//        }};
//    }

    @Override
    protected Pane pane() {
        return new EvaluationFillingGeneral(mockGui, ImmutableMap.of()).pane();
    }

    @Override
    protected String backButtonTarget() {
        return Keys.GUI_VIEW_EVALUATION_FILLING;
    }

    @Override
    protected Boolean shouldHaveQuitButton() {
        return true;
    }

    @Override
    protected Gui gui() {
        return mockGui;
    }
}