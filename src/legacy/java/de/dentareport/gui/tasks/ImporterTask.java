package de.dentareport.gui.tasks;

import de.dentareport.Metadata;
import de.dentareport.evaluations.office.Office;
import de.dentareport.exceptions.DentereportInterruptedException;
import de.dentareport.gui.ProgressUpdate;
import de.dentareport.imports.dampsoft.Dampsoft;
import de.dentareport.repositories.ValidCasesRepository;
import de.dentareport.utils.Keys;
import de.dentareport.utils.string.DateStringUtils;

// TODO: TEST?
public class ImporterTask extends BaseTask {

    @Override
    protected Void call() {
        try {
            Metadata.delete(Keys.METADATA_KEY_VALID_IMPORT);
            ProgressUpdate.setTask(this);
            Dampsoft dampsoft = new Dampsoft();
            dampsoft.importData();

            ValidCasesRepository validCasesRepository = new ValidCasesRepository();
            validCasesRepository.identifyValidCases();

            Office office = new Office();
            office.evaluate();

            updateMessage(Keys.GUI_TEXT_DONE);
            ProgressUpdate.finished();
            Metadata.set(Keys.METADATA_KEY_VALID_IMPORT, DateStringUtils.now());
            return null;
        } catch (DentereportInterruptedException e) {
            return null;
        }
    }
}