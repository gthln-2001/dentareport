package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableMap;
import de.dentareport.gui.BaseFxElementTest;
import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import de.dentareport.gui.tasks.ImporterTask;
import de.dentareport.utils.Keys;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class ImportTest extends BaseFxElementTest {

    @Mocked
    Gui mockGui;
    private Import importPane;

    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        Assertions.assertThat(true).isTrue();
    }


    // TODO: FIx test
//    @Test
//    public void it_starts_new_import_when_required_files_exist(@Mocked ImporterTask mockImporterTask,
//                                                               @Mocked Thread mockThread,
//                                                               @Mocked Dampsoft mockDampsoft) {
//
//        new Expectations() {{
//            mockDampsoft.missingFiles();
//            result = ImmutableList.of();
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_DATA_IMPORT);
//
//        new Verifications() {{
//            new Thread(new ImporterTask());
//            mockThread.start();
//            times = 1;
//        }};
//    }
//
//    @Test
//    public void it_does_not_start_new_import_and_shows_info_box_when_required_files_do_not_exist(
//            @Mocked Thread mockThread,
//            @Mocked Dampsoft mockDampsoft,
//            @Mocked Element mockElement) {
//
//        ImmutableList<String> missingFiles = ImmutableList.of("missing_file_1", "missing_file_2");
//        new Expectations() {{
//            mockDampsoft.missingFiles();
//            result = missingFiles;
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_DATA_IMPORT);
//
//        new Verifications() {{
//            Element.infoBox()
//                    .title(Keys.GUI_TEXT_DENTAREPORT)
//                    .text(String.format("%s%s",
//                            Keys.GUI_TEXT_MISSING_FILES_FOR_IMPORT,
//                            String.join("\n", missingFiles)))
//                    .create();
//            times = 1;
//            mockThread.start();
//            times = 0;
//        }};
//    }
//
//    @Test
//    public void it_disables_some_buttons_when_runnning_import(@Mocked ImporterTask mockImporterTask,
//                                                              @Mocked Thread mockThread,
//                                                              @Mocked Dampsoft mockDampsoft) {
//        new Expectations() {{
//            mockDampsoft.missingFiles();
//            result = ImmutableList.of();
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_DATA_IMPORT);
//
//        List<String> provider = ImmutableList.of(
//                "#button-back",
//                "#button-quit",
//                "#button-start-import"
//        );
//
//        for (String entry : provider) {
//            Button button = find(entry);
//            assertThat(button.isDisabled()).isTrue();
//        }
//    }
//
//    @Test
//    public void it_enables_cancel_button_when_running_import(@Mocked ImporterTask mockImporterTask,
//                                                             @Mocked Thread mockThread,
//                                                             @Mocked Dampsoft mockDampsoft) {
//        new Expectations() {{
//            mockDampsoft.missingFiles();
//            result = ImmutableList.of();
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_DATA_IMPORT);
//
//        Button button = find("#button-cancel-import");
//        assertThat(button.isDisabled()).isFalse();
//    }
//
//    @Test
//    public void it_cancels_running_import_after_confirmation(@Mocked ImporterTask mockImporterTask,
//                                                             @Mocked Thread mockThread,
//                                                             @Mocked Element mockElement,
//                                                             @Mocked Dampsoft mockDampsoft) {
//
//        new Expectations() {{
//            mockDampsoft.missingFiles();
//            result = ImmutableList.of();
//            new Thread(new ImporterTask());
//            Element.confirmBox()
//                    .title(Keys.GUI_WINDOW_TITLE_CONFIRM_CANCEL_IMPORT)
//                    .text(Keys.GUI_TEXT_CONFIRM_CANCEL_IMPORT)
//                    .create();
//            result = true;
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_DATA_IMPORT);
//        clickOn(Keys.GUI_TEXT_CANCEL_DATA_IMPORT);
//
//        new Verifications() {{
//            mockThread.interrupt();
//            times = 1;
//        }};
//    }
//
//    @Test
//    public void it_does_not_cancel_running_import_when_not_confirmed(@Mocked ImporterTask mockImporterTask,
//                                                                     @Mocked Thread mockThread,
//                                                                     @Mocked Element mockElement,
//                                                                     @Mocked Dampsoft mockDampsoft) {
//
//        new Expectations() {{
//            mockDampsoft.missingFiles();
//            result = ImmutableList.of();
//            new Thread(new ImporterTask());
//            Element.confirmBox()
//                    .title(Keys.GUI_WINDOW_TITLE_CONFIRM_CANCEL_IMPORT)
//                    .text(Keys.GUI_TEXT_CONFIRM_CANCEL_IMPORT)
//                    .create();
//            result = false;
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_DATA_IMPORT);
//        clickOn(Keys.GUI_TEXT_CANCEL_DATA_IMPORT);
//
//        new Verifications() {{
//            mockThread.interrupt();
//            times = 0;
//        }};
//    }
//
//    @Test
//    public void it_enables_some_buttons_when_import_is_canceled(@Mocked ImporterTask mockImporterTask,
//                                                                @Mocked Thread mockThread,
//                                                                @Mocked Element mockElement,
//                                                                @Mocked Dampsoft mockDampsoft) {
//        new Expectations() {{
//            mockDampsoft.missingFiles();
//            result = ImmutableList.of();
//            new Thread(new ImporterTask());
//            Element.confirmBox()
//                    .title(anyString)
//                    .text(anyString)
//                    .create();
//            result = true;
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_DATA_IMPORT);
//        clickOn(Keys.GUI_TEXT_CANCEL_DATA_IMPORT);
//
//        List<String> provider = ImmutableList.of(
//                "#button-back",
//                "#button-quit",
//                "#button-start-import"
//        );
//
//        for (String entry : provider) {
//            Button button = find(entry);
//            assertThat(button.isDisabled()).isFalse();
//        }
//    }
//
//    @Test
//    public void it_disables_cancel_button_when_evaluation_is_canceled(@Mocked ImporterTask mockImporterTask,
//                                                                      @Mocked Thread mockThread,
//                                                                      @Mocked Element mockElement,
//                                                                      @Mocked Dampsoft mockDampsoft) {
//        new Expectations() {{
//            mockDampsoft.missingFiles();
//            result = ImmutableList.of();
//            new Thread(new ImporterTask());
//            Element.confirmBox()
//                    .title(anyString)
//                    .text(anyString)
//                    .create();
//            result = true;
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_DATA_IMPORT);
//        clickOn(Keys.GUI_TEXT_CANCEL_DATA_IMPORT);
//
//        Button button = find("#button-cancel-import");
//        assertThat(button.isDisabled()).isTrue();
//    }
//
//    @Test
//    public void it_enables_some_buttons_when_import_is_finished(@Mocked ImporterTask mockImporterTask,
//                                                                @Mocked Thread mockThread,
//                                                                @Mocked Dampsoft mockDampsoft) {
//
//        new Expectations() {{
//            mockDampsoft.missingFiles();
//            result = ImmutableList.of();
//            new Thread(new ImporterTask());
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_DATA_IMPORT);
//        importPane.finished();
//
//        List<String> provider = ImmutableList.of(
//                "#button-back",
//                "#button-quit"
//        );
//
//        for (String entry : provider) {
//            Button button = find(entry);
//            assertThat(button.isDisabled()).isFalse();
//        }
//    }
//
//    @Test
//    public void it_hides_button_to_access_evaluations_by_default() {
//        Button button = find("#button-evaluation");
//        assertThat(button.visibleProperty().get()).isFalse();
//    }
//
//    @Test
//    public void it_shows_button_to_access_evaluations_when_import_is_finished(
//            @Mocked ImporterTask mockImporterTask,
//            @Mocked Thread mockThread,
//            @Mocked Dampsoft mockDampsoft) {
//
//        new Expectations() {{
//            mockDampsoft.missingFiles();
//            result = ImmutableList.of();
//            new Thread(new ImporterTask());
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_DATA_IMPORT);
//        importPane.finished();
//
//        Button button = find("#button-evaluation");
//        assertThat(button.visibleProperty().get()).isTrue();
//
//        clickOn(Keys.GUI_TEXT_EVALUATIONS);
//        new Verifications() {{
//            mockGui.setContentPane(Keys.GUI_VIEW_EVALUATION);
//        }};
//    }
//
//    @Test
//    public void it_disables_cancel_button_when_import_is_finished(@Mocked ImporterTask mockImporterTask,
//                                                                  @Mocked Thread mockThread,
//                                                                  @Mocked Dampsoft mockDampsoft) {
//        new Expectations() {{
//            mockDampsoft.missingFiles();
//            result = ImmutableList.of();
//            new Thread(new ImporterTask());
//        }};
//
//        clickOn(Keys.GUI_TEXT_START_DATA_IMPORT);
//        importPane.finished();
//
//        Button button = find("#button-cancel-import");
//        assertThat(button.isDisabled()).isTrue();
//    }

    @Override
    protected Pane pane() {
        importPane = new Import(mockGui, ImmutableMap.of());
        return importPane.pane();
    }

    @Override
    protected String backButtonTarget() {
        return Keys.GUI_VIEW_COPY_FILES;
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