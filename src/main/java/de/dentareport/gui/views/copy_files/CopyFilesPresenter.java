package de.dentareport.gui.views.copy_files;

import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.imports.dampsoft.Dampsoft;

import static de.dentareport.utils.Keys.GUI_TEXT_COPY_FILES_MANUALLY_FOR_IMPORT;
import static de.dentareport.utils.Keys.GUI_TEXT_FUNCTION_NOT_AVAILABLE_IN_DEMO_VERSION;

// TODO: Test?
// TODO: TEST?
public class CopyFilesPresenter {

    private final UiController uiController;

    public CopyFilesPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onBack() {
        uiController.showView(ViewId.START);
    }

    public void onCopyFilesManually() {
        uiController.showInfo(String.format("%s\n%s",
                GUI_TEXT_COPY_FILES_MANUALLY_FOR_IMPORT,
                String.join("\n", new Dampsoft().requiredFiles())));
        uiController.showView(ViewId.IMPORT_DATA);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public void onNotAvailableInDemo() {
        uiController.showInfo(GUI_TEXT_FUNCTION_NOT_AVAILABLE_IN_DEMO_VERSION);
    }


}

