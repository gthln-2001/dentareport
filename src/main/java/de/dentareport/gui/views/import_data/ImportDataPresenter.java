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
// TODO: TEST?
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
        view.startImport();
        importData();
    }

    public void onEvaluations() {
        uiController.showView(ViewId.EVALUATION);
    }

    public void setView(ImportDataView view) {
        this.view = view;
    }

    private void importData() {
        SwingWorker<Void, ProgressUpdate> worker =
                new SwingWorker<>() {

                    @Override
                    protected Void doInBackground() {
                        Dampsoft dampsoft = new Dampsoft();
                        dampsoft.importData(
                                (percent, message) -> publish(new ProgressUpdate(percent, message, false)),
                                (percent, message) -> publish(new ProgressUpdate(percent, message, true))
                        );
                        return null;
                    }

                    @Override
                    protected void process(List<ProgressUpdate> chunks) {
                        ProgressUpdate last = chunks.getLast();
                        if (last.fileProgress()) {
                            view.setFileProgress(last.percent());
                            view.setFileProgressText(last.message());
                        } else {
                            view.setOverallProgress(last.percent());
                            view.setOverallProgressText(last.message());
                        }
                    }

                    @Override
                    protected void done() {
                        ValidCasesRepository validCasesRepository = new ValidCasesRepository();
                        validCasesRepository.identifyValidCases();

                        Office office = new Office();
                        office.evaluate();

                        view.setOverallProgress(100);
                        view.setOverallProgressText(Keys.GUI_TEXT_DONE);
                        view.setFileProgress(0);
                        view.setFileProgressText("");
                        Metadata.set(Keys.METADATA_KEY_VALID_IMPORT, DateStringUtils.now());

                        view.importDone();
                    }
                };

        worker.execute();
    }
}

