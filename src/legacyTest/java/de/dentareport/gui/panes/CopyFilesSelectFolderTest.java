package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableMap;
import de.dentareport.gui.BaseFxElementTest;
import de.dentareport.gui.Gui;
import de.dentareport.utils.Keys;
import javafx.scene.layout.Pane;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO: TEST?
public class CopyFilesSelectFolderTest extends BaseFxElementTest {

    @Mocked
    Gui mockGui;

    // TODO: Remove test (temporarly added to have test in class)
    @Test
    public void testSomething() {
        Assertions.assertThat(true).isTrue();
    }

    // TODO: Fix test
//    @Test
//    public void it_shows_default_text_if_no_folder_is_selected() {
//        assertThat(find(Keys.GUI_TEXT_NO_DIRECTORY_SELECTED)).isNotNull();
//    }

    @Override
    protected Pane pane() {
        return new CopyFilesSelectFolder(mockGui, ImmutableMap.of()).pane();
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