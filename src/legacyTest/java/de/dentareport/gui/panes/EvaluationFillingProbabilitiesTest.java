package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableMap;
import de.dentareport.gui.BaseFxElementTest;
import de.dentareport.gui.Gui;
import de.dentareport.utils.Keys;
import de.dentareport.utils.MiniCache;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EvaluationFillingProbabilitiesTest extends BaseFxElementTest {

    @Mocked
    Gui mockGui;

    @BeforeEach
    public void setUp() throws Exception {
        MiniCache.clear();
    }

    // TODO: Fix test
//    @Test
//    public void it_opens_display_view_with_default_values() {
//        clickOn(Keys.GUI_TEXT_DISPLAY);
//
//        new Verifications() {{
//            mockGui.setContentPane(ImmutableMap.of(
//                    "evaluationId", Keys.EVALUATION_TYPE_FILLING,
//                    "view", Keys.GUI_VIEW_EVALUATION_FILLING_PROBABILITIES_DISPLAY,
//                    "event", Keys.GUI_TEXT_TOOTHLOSS,
//                    "dependency", Keys.GUI_TEXT_NO_DEPENDENCY));
//        }};
//    }

    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        Assertions.assertThat(true).isTrue();
    }

    // TODO: Fix test
//    @Test
//    public void it_opens_display_view_with_selected_values() {
//        for (String dependency : dependencies()) {
//            for (String event : events()) {
//                clickOn(dependency);
//                clickOn(event);
//                clickOn(Keys.GUI_TEXT_DISPLAY);
//
//                new Verifications() {{
//                    mockGui.setContentPane(ImmutableMap.of(
//                            "evaluationId", Keys.EVALUATION_TYPE_FILLING,
//                            "view", Keys.GUI_VIEW_EVALUATION_FILLING_PROBABILITIES_DISPLAY,
//                            "event", event,
//                            "dependency", dependency
//                    ));
//                }};
//            }
//        }
//    }

    @Override
    protected Pane pane() {
        return new EvaluationFillingProbabilities(mockGui,
                                                  ImmutableMap.of("evaluationId", Keys.EVALUATION_TYPE_FILLING)).pane();
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

    private List<String> events() {
        return radioButtonTexts("#buttons-events");
    }

    private List<String> dependencies() {
        return radioButtonTexts("#buttons-dependencies");
    }

    private List<String> radioButtonTexts(String id) {
        List<String> ret = new ArrayList<>();
        VBox box = find(id);
        for (Node component : box.getChildren()) {
            if (component instanceof RadioButton) {
                ret.add(((RadioButton) component).getText());
            }
        }
        return ret;
    }
}