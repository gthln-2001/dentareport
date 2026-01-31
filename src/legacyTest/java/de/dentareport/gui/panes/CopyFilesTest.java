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

// TODO: TEST?
public class CopyFilesTest extends BaseFxElementTest {

    @Mocked
    Gui mockGui;
    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        Assertions.assertThat(true).isTrue();
    }

    // TODO: Fix test

//    @Test
//    public void it_opens_view_to_copy_files_automatically(@Mocked Element mockElement) {
//        clickOn(Keys.GUI_TEXT_COPY_FILES_AUTOMATIC);
//
//        new Verifications() {{
//            Element.notAvailableInDemoVersion();
//            times = 1;
//        }};
//    }

    // TODO: Fix test
//    @Test
//    public void it_opens_view_to_select_folder_to_copy_files_from() {
//        clickOn(Keys.GUI_TEXT_COPY_FILES_SELECT_FOLDER);
//
//        new Verifications() {{
//            mockGui.setContentPane(Keys.GUI_VIEW_COPY_FILES_SELECT_FOLDER);
//            times = 1;
//        }};
//    }

    // TODO: Fix test
//    @Test
//    public void it_can_skip_file_copying_when_files_were_manually_copied(@Mocked Element mockElement,
//                                                                         @Mocked Dampsoft mockDampsoft) {
//        List<String> requiredFiles = ImmutableList.of("foo", "bar");
//        new Expectations() {{
//            mockDampsoft.requiredFiles();
//            result = requiredFiles;
//        }};
//
//        clickOn(Keys.GUI_TEXT_COPY_FILES_MANUALLY);
//
//        new Verifications() {{
//            Element.infoBox()
//                    .text(String.format("%s\n%s",
//                            Keys.GUI_TEXT_COPY_FILES_MANUALLY_FOR_IMPORT,
//                            String.join("\n", requiredFiles)))
//                    .create();
//            times = 1;
//            mockGui.setContentPane(Keys.GUI_VIEW_IMPORT);
//            times = 1;
//        }};
//    }

    @Override
    protected Pane pane() {
        return new CopyFiles(mockGui, ImmutableMap.of()).pane();
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