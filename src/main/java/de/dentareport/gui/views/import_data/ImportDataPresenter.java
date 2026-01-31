package de.dentareport.gui.views.import_data;

import de.dentareport.Metadata;
import de.dentareport.evaluations.office.Office;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.imports.dampsoft.Dampsoft;
import de.dentareport.repositories.ValidCasesRepository;
import de.dentareport.utils.Keys;
import de.dentareport.utils.string.DateStringUtils;

// TODO: Test?
public class ImportDataPresenter {

    private final UiController uiController;

    public ImportDataPresenter(UiController uiController) {
        this.uiController = uiController;
    }

    public void onBack() {
        uiController.showView(ViewId.COPY_FILES);
    }

    public void onExitRequested() {
        uiController.confirmExit();
    }

    public void onStartDataImport() {
        Metadata.delete(Keys.METADATA_KEY_VALID_IMPORT);
//        ProgressUpdate.setTask(this);
        Dampsoft dampsoft = new Dampsoft();
        dampsoft.importData();

        ValidCasesRepository validCasesRepository = new ValidCasesRepository();
        validCasesRepository.identifyValidCases();

        Office office = new Office();
        office.evaluate();

//        updateMessage(Keys.GUI_TEXT_DONE);
//        ProgressUpdate.finished();
        Metadata.set(Keys.METADATA_KEY_VALID_IMPORT, DateStringUtils.now());
    }


}

