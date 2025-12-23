package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableMap;
import de.dentareport.gui.BaseFxElementTest;
import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import de.dentareport.gui.tasks.EvaluatorTask;
import de.dentareport.utils.Keys;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EvaluationFillingTest extends BaseFxElementTest {

    @Mocked
    Gui mockGui;
    private EvaluationFilling evaluationFilling;

    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        Assertions.assertThat(true).isTrue();
    }

    // TODO: Fix test
//    @Test
//    public void it_opens_general_information_view_when_valid_evaluation_exists(@Mocked Metadata mockMetaData) {
//        new Expectations() {{
//            Metadata.has(Keys.METADATA_KEY_VALID_EVALUATION_FILLING);
//            result = true;
//        }};
//
//        clickOn(Keys.GUI_TEXT_GENERAL_INFORMATION);
//
//        new Verifications() {{
//            mockGui.setContentPane(ImmutableMap.of("view",
//                                                   Keys.GUI_VIEW_EVALUATION_FILLING_GENERAL,
//                                                   "evaluationId",
//                                                   "7"));
//            times = 1;
//        }};
//    }

    // TODO: Fix test
//    @Test
//    public void it_shows_info_box_when_trying_to_open_general_information_view_without_valid_evaluation(
//            @Mocked Metadata mockMetaData,
//            @Mocked Element mockElement) {
//
//        new Expectations() {{
//            Metadata.has(Keys.METADATA_KEY_VALID_EVALUATION_FILLING);
//            result = false;
//        }};
//
//        clickOn(Keys.GUI_TEXT_GENERAL_INFORMATION);
//
//        new Verifications() {{
//            Element.infoBox()
//                   .title(Keys.GUI_TEXT_DENTAREPORT)
//                   .text(Keys.GUI_TEXT_ERROR_NO_VALID_EVALUATION_FILLING)
//                   .create();
//            mockGui.setContentPane(Keys.GUI_VIEW_EVALUATION_FILLING_GENERAL);
//            times = 0;
//        }};
//    }

    // TODO: Fix test
//    @Test
//    public void it_opens_probabilites_view_when_valid_evaluation_exists(@Mocked Metadata mockMetaData) {
//        new Expectations() {{
//            Metadata.has(Keys.METADATA_KEY_VALID_EVALUATION_FILLING);
//            result = true;
//        }};
//
//        clickOn(Keys.GUI_TEXT_PROBABILITIES);
//
//        new Verifications() {{
//            mockGui.setContentPane(ImmutableMap.of("view", Keys.GUI_VIEW_EVALUATION_FILLING_PROBABILITIES,
//                                                   "evaluationId", Keys.EVALUATION_TYPE_FILLING));
//            times = 1;
//        }};
//    }

    // TODO: Fix test
//    @Test
//    public void it_shows_info_box_when_trying_to_open_probabilites_view_without_valid_evaluation(
//            @Mocked Metadata mockMetaData,
//            @Mocked Element mockElement) {
//
//        new Expectations() {{
//            Metadata.has(Keys.METADATA_KEY_VALID_EVALUATION_FILLING);
//            result = false;
//        }};
//
//        clickOn(Keys.GUI_TEXT_PROBABILITIES);
//
//        new Verifications() {{
//            Element.infoBox()
//                   .title(Keys.GUI_TEXT_DENTAREPORT)
//                   .text(Keys.GUI_TEXT_ERROR_NO_VALID_EVALUATION_FILLING)
//                   .create();
//            times = 1;
//            mockGui.setContentPane(Keys.GUI_VIEW_EVALUATION_FILLING_PROBABILITIES);
//            times = 0;
//        }};
//    }

    // TODO: Fix test
//    @Test
//    public void it_starts_new_evaluation(@Mocked EvaluatorTask mockEvaluatorTask,
//                                         @Mocked Thread mockThread) {
//        clickOn(Keys.GUI_TEXT_START_NEW_EVALUATION);
//
//        new Verifications() {{
//            new Thread(new EvaluatorTask(Keys.METADATA_KEY_VALID_EVALUATION_FILLING, Evaluation7.class));
//            mockThread.start();
//            times = 1;
//        }};
//    }

    // TODO: Fix test
//    @Test
//    public void it_disables_some_buttons_when_runnning_evaluation(@Mocked EvaluatorTask mockEvaluatorTask,
//                                                                  @Mocked Thread mockThread) {
//        clickOn(Keys.GUI_TEXT_START_NEW_EVALUATION);
//
//        List<String> provider = ImmutableList.of(
//                "#button-back",
//                "#button-quit",
//                "#button-probabilities",
//                "#button-general-information",
//                "#button-start-evaluation"
//        );
//
//        for (String entry : provider) {
//            Button button = find(entry);
//            assertThat(button.isDisabled()).isTrue();
//        }
//    }

    // TODO: Fix test
//    @Test
//    public void it_enables_cancel_button_when_running_evaluation(@Mocked EvaluatorTask mockEvaluatorTask,
//                                                                 @Mocked Thread mockThread) {
//        clickOn(Keys.GUI_TEXT_START_NEW_EVALUATION);
//
//        Button button = find("#button-cancel-evaluation");
//        assertThat(button.isDisabled()).isFalse();
//    }

    // TODO: Fix test
//    @Test
//    public void it_cancels_running_evaluation_after_confirmation(@Mocked EvaluatorTask mockEvaluatorTask,
//                                                                 @Mocked Thread mockThread,
//                                                                 @Mocked Element mockElement) {
//
//        new Expectations() {{
//            new Thread(new EvaluatorTask(Keys.METADATA_KEY_VALID_EVALUATION_FILLING, Evaluation7.class));
//            Element.confirmBox()
//                   .title(Keys.GUI_WINDOW_TITLE_CONFIRM_CANCEL_EVALUATION)
//                   .text(Keys.GUI_TEXT_CONFIRM_CANCEL_EVALUATION)
//                   .create();
//            result = true;
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_NEW_EVALUATION);
//        clickOn(Keys.GUI_TEXT_CANCEL_EVALUATION);
//
//        new Verifications() {{
//            mockThread.interrupt();
//            times = 1;
//        }};
//    }

    // TODO: Fix test
//    @Test
//    public void it_does_not_cancel_running_evaluation_when_not_confirmed(
//            @Mocked EvaluatorTask mockEvaluatorTask,
//            @Mocked Thread mockThread,
//            @Mocked Element mockElement) {
//
//        new Expectations() {{
//            new Thread(new EvaluatorTask(Keys.METADATA_KEY_VALID_EVALUATION_FILLING, Evaluation7.class));
//            Element.confirmBox()
//                   .title(Keys.GUI_WINDOW_TITLE_CONFIRM_CANCEL_EVALUATION)
//                   .text(Keys.GUI_TEXT_CONFIRM_CANCEL_EVALUATION)
//                   .create();
//            result = false;
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_NEW_EVALUATION);
//        clickOn(Keys.GUI_TEXT_CANCEL_EVALUATION);
//
//        new Verifications() {{
//            mockThread.interrupt();
//            times = 0;
//        }};
//    }

    // TODO: Fix test
//    @Test
//    public void it_enables_some_buttons_when_evaluation_is_canceled(
//            @Mocked EvaluatorTask mockEvaluatorTask,
//            @Mocked Thread mockThread,
//            @Mocked Element mockElement) {
//
//        new Expectations() {{
//            new Thread(new EvaluatorTask(Keys.METADATA_KEY_VALID_EVALUATION_FILLING, Evaluation7.class));
//            Element.confirmBox()
//                   .title(Keys.GUI_WINDOW_TITLE_CONFIRM_CANCEL_EVALUATION)
//                   .text(Keys.GUI_TEXT_CONFIRM_CANCEL_EVALUATION)
//                   .create();
//            result = true;
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_NEW_EVALUATION);
//        clickOn(Keys.GUI_TEXT_CANCEL_EVALUATION);
//
//        List<String> provider = ImmutableList.of(
//                "#button-back",
//                "#button-quit",
//                "#button-probabilities",
//                "#button-general-information",
//                "#button-start-evaluation"
//        );
//
//        for (String entry : provider) {
//            Button button = find(entry);
//            assertThat(button.isDisabled()).isFalse();
//        }
//    }

    // TODO: Fix test
//    @Test
//    public void it_disables_cancel_button_when_evaluation_is_canceled(
//            @Mocked EvaluatorTask mockEvaluatorTask,
//            @Mocked Thread mockThread,
//            @Mocked Element mockElement) {
//
//        new Expectations() {{
//            new Thread(new EvaluatorTask(Keys.METADATA_KEY_VALID_EVALUATION_FILLING, Evaluation7.class));
//            Element.confirmBox()
//                   .title(Keys.GUI_WINDOW_TITLE_CONFIRM_CANCEL_EVALUATION)
//                   .text(Keys.GUI_TEXT_CONFIRM_CANCEL_EVALUATION)
//                   .create();
//            result = true;
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_NEW_EVALUATION);
//        clickOn(Keys.GUI_TEXT_CANCEL_EVALUATION);
//
//        Button button = find("#button-cancel-evaluation");
//        assertThat(button.isDisabled()).isTrue();
//    }

    // TODO: Fix test
//    @Test
//    public void it_enables_some_buttons_when_evaluation_is_finished(@Mocked EvaluatorTask mockEvaluatorTask,
//                                                                    @Mocked Thread mockThread) {
//
//        new Expectations() {{
//            new Thread(new EvaluatorTask(Keys.METADATA_KEY_VALID_EVALUATION_FILLING, Evaluation7.class));
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_NEW_EVALUATION);
//        evaluationFilling.finished();
//
//        List<String> provider = ImmutableList.of(
//                "#button-back",
//                "#button-quit",
//                "#button-probabilities",
//                "#button-general-information",
//                "#button-start-evaluation"
//        );
//
//        for (String entry : provider) {
//            Button button = find(entry);
//            assertThat(button.isDisabled()).isFalse();
//        }
//    }

    // TODO: Fix test
//    @Test
//    public void it_disables_cancel_button_when_evaluation_is_finished(@Mocked EvaluatorTask mockEvaluatorTask,
//                                                                      @Mocked Thread mockThread) {
//
//        clickOn(Keys.GUI_TEXT_START_NEW_EVALUATION);
//        evaluationFilling.finished();
//
//        Button button = find("#button-cancel-evaluation");
//        assertThat(button.isDisabled()).isTrue();
//    }

    @Override
    protected Pane pane() {
        evaluationFilling = new EvaluationFilling(mockGui, ImmutableMap.of());
        return evaluationFilling.pane();
    }

    @Override
    protected String backButtonTarget() {
        return Keys.GUI_VIEW_EVALUATION;
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