package de.dentareport.gui.views.import_data;

import de.dentareport.Metadata;
import de.dentareport.evaluations.office.Office;
import de.dentareport.gui.app.UiController;
import de.dentareport.gui.navigation.ViewId;
import de.dentareport.gui.progress.ProgressUpdate;
import de.dentareport.imports.dampsoft.Dampsoft;
import de.dentareport.repositories.ValidCasesRepository;
import de.dentareport.utils.Keys;
import de.dentareport.utils.string.DateStringUtils;

import javax.swing.*;
import java.util.List;

// TODO: Test?
public class ImportDataPresenter {

    private final UiController uiController;
    private ImportDataView view;

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

        SwingWorker<Void, ProgressUpdate> worker =
                new SwingWorker<>() {

                    @Override
                    protected Void doInBackground() {

                        Dampsoft dampsoft = new Dampsoft();

                        dampsoft.importData((percent, message) -> {
                            publish(new ProgressUpdate(percent, message));
                        });

                        return null;
                    }

                    @Override
                    protected void process(List<ProgressUpdate> chunks) {

                        ProgressUpdate last = chunks.get(chunks.size() - 1);

                        view.setProgress(last.percent());
                        view.setProgressText(last.message());
                    }

                    @Override
                    protected void done() {
                        view.setProgress(100);
                        view.setProgressText("Done");
                        Metadata.set(Keys.METADATA_KEY_VALID_IMPORT,
                                DateStringUtils.now());
                    }
                };

        worker.execute();


//        ProgressUpdate.setTask(this);
//        Dampsoft dampsoft = new Dampsoft();
//        dampsoft.importData();

//        ValidCasesRepository validCasesRepository = new ValidCasesRepository();
//        validCasesRepository.identifyValidCases();
//
//        Office office = new Office();
//        office.evaluate();

//        updateMessage(Keys.GUI_TEXT_DONE);
//        ProgressUpdate.finished();
        Metadata.set(Keys.METADATA_KEY_VALID_IMPORT, DateStringUtils.now());
    }

    public void setView(ImportDataView view) {
        this.view = view;
    }


}

