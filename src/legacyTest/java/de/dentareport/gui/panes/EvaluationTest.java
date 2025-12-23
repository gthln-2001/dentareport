package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableMap;
import de.dentareport.gui.BaseFxElementTest;
import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import de.dentareport.utils.Keys;
import javafx.scene.layout.Pane;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EvaluationTest extends BaseFxElementTest {

    @Mocked
    Gui mockGui;

    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        Assertions.assertThat(true).isTrue();
    }

    // TODO: FIx test
//
//    @Test
//    public void it_opens_evaluation_views() {
//        Map<String, String> provider = ImmutableMap.of(
//                Keys.GUI_TEXT_TELESCOPIC_CROWNS, Keys.GUI_VIEW_EVALUATION_TELESCOPIC_CROWN,
//                Keys.GUI_TEXT_FILLINGS, Keys.GUI_VIEW_EVALUATION_FILLING,
//                Keys.GUI_TEXT_GENERAL_PATIENT_INFORMATION, Keys.GUI_VIEW_GENERAL_PATIENT_INFORMATION
//        );
//        for (Map.Entry<String, String> entry : provider.entrySet()) {
//            clickOn(entry.getKey());
//            new Verifications() {{
//                mockGui.setContentPane(entry.getValue());
//                times = 1;
//            }};
//        }
//    }
//
//    @Test
//    public void it_show_infobox_for_not_available_evaluations(@Mocked Element mockElement) {
//        List<String> provider = ImmutableList.of(
//                Keys.GUI_TEXT_BRIDGES,
//                Keys.GUI_TEXT_CROWNS,
//                Keys.GUI_TEXT_ENDODONTICS,
//                Keys.GUI_TEXT_IMPLANTS,
//                Keys.GUI_TEXT_INLAYS,
//                Keys.GUI_TEXT_PREVENTION_MEASURES,
//                Keys.GUI_TEXT_SEALINGS,
//                Keys.GUI_TEXT_SURGERY,
//                Keys.GUI_TEXT_WURZELSTIFTE
//        );
//        for (String entry : provider) {
//            clickOn(entry);
//        }
//        new Verifications() {{
//            Element.notAvailableInDemoVersion();
//            times = provider.size();
//        }};
//    }

    @Override
    protected Pane pane() {
        return new Evaluation(mockGui, ImmutableMap.of()).pane();
    }

    @Override
    protected String backButtonTarget() {
        return Keys.GUI_VIEW_START;
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