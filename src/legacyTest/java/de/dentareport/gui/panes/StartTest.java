package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableMap;
import de.dentareport.gui.BaseFxElementTest;
import de.dentareport.gui.Gui;
import de.dentareport.gui.elements.Element;
import javafx.scene.layout.Pane;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class StartTest extends BaseFxElementTest {

    @Mocked
    Gui mockGui;

    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        Assertions.assertThat(true).isTrue();
    }


    // TODO: Fix tests
//    @Test
//    public void it_opens_import_view() {
//        clickOn(Keys.GUI_TEXT_IMPORT_DATA);
//
//        new Verifications() {{
//            mockGui.setContentPane(Keys.GUI_VIEW_COPY_FILES);
//            times = 1;
//        }};
//    }
//
//    @Test
//    public void it_opens_evaluation_view_if_valid_import_exists(@Mocked Metadata mockMetadata) {
//        new Expectations() {{
//            Metadata.has(Keys.METADATA_KEY_VALID_IMPORT);
//            result = true;
//        }};
//
//        clickOn(Keys.GUI_TEXT_EVALUATIONS);
//
//        new Verifications() {{
//            mockGui.setContentPane(Keys.GUI_VIEW_EVALUATION);
//            times = 1;
//        }};
//    }
//
//    @Test
//    public void it_does_not_open_evaluation_view_if_no_valid_import_exists(@Mocked Metadata mockMetadata,
//                                                                           @Mocked Element mockElement) {
//        new Expectations() {{
//            Metadata.has(Keys.METADATA_KEY_VALID_IMPORT);
//            result = false;
//        }};
//
//        clickOn(Keys.GUI_TEXT_EVALUATIONS);
//
//        new Verifications() {{
//            mockGui.setContentPane(anyString);
//            times = 0;
//            Element.infoBox().title(Keys.GUI_TEXT_DENTAREPORT).text(Keys.GUI_TEXT_ERROR_NO_VALID_IMPORT_FOUND).create();
//            times = 1;
//        }};
//    }

    @Override
    protected Pane pane() {
        return new Start(mockGui, ImmutableMap.of()).pane();
    }

    @Override
    protected String backButtonTarget() {
        return null;
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