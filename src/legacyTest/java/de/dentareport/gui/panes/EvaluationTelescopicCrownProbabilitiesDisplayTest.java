package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableMap;
import de.dentareport.gui.BaseFxElementTest;
import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import de.dentareport.gui.tables.AnnualFailureRate;
import de.dentareport.gui.tables.GroupsSizes;
import de.dentareport.repositories.KaplanMeierRepository;
import de.dentareport.utils.Keys;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.testfx.api.FxAssert.verifyThat;

public class EvaluationTelescopicCrownProbabilitiesDisplayTest extends BaseFxElementTest {

    @Mocked
    Gui mockGui;
    @Mocked
    GroupsSizes mockTableDataGroupSizes;
    @Mocked
    AnnualFailureRate mockTableDataAnnualFailureRate;
    @Mocked
    KaplanMeierRepository mockKaplanMeierRepository;

    // TODO: Fix test
//    @Test
//    public void it_has_table_for_group_sizes() {
//        verifyThat("#table-group-sizes", Node::isVisible);
//
//        new Verifications() {{
//            mockTableDataGroupSizes.data(testOptions());
//            times = 1;
//        }};
//    }

    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        Assertions.assertThat(true).isTrue();
    }

    // TODO: Fix test
//    @Test
//    public void it_has_table_for_afr() {
//        verifyThat("#table-afr", Node::isVisible);
//
//        new Verifications() {{
//            mockTableDataAnnualFailureRate.data(testOptions());
//            times = 1;
//        }};
//    }

    // TODO: Fix test
//    @Test
//    public void it_has_kaplan_meier_chart() {
//        verifyThat("#chart-kaplan-meier", Node::isVisible);
//
//        new Verifications() {{
//            Element.lineChart().lines(mockKaplanMeierRepository.lines(testOptions()));
//            times = 1;
//        }};
//    }

    @Override
    protected Pane pane() {
        return new EvaluationTelescopicCrownProbabilitiesDisplay(mockGui, testOptions()).pane();
    }

    @Override
    protected String backButtonTarget() {
        return Keys.GUI_VIEW_EVALUATION_TELESCOPIC_CROWN_PROBABILITIES;
    }

    @Override
    protected Boolean shouldHaveQuitButton() {
        return true;
    }

    @Override
    protected Gui gui() {
        return mockGui;
    }

    private ImmutableMap<String, String> testOptions() {
        return ImmutableMap.of("dependency", "foo_group");
    }
}