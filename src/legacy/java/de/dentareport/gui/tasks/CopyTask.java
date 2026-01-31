package de.dentareport.gui.tasks;

import de.dentareport.Config;
import de.dentareport.exceptions.DentareportIOException;
import de.dentareport.exceptions.DentereportInterruptedException;
import de.dentareport.gui.ProgressUpdate;
import de.dentareport.utils.Keys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

// TODO: TEST?
public class CopyTask extends BaseTask {

    private List<String> filenames;
    private File selectedDirectory;

    public CopyTask(List<String> filenames, File selectedDirectory) {
        super();
        this.filenames = filenames;
        this.selectedDirectory = selectedDirectory;
    }

    @Override
    public Void call() {
        try {
            ProgressUpdate.setTask(this);
            for (String filename : filenames) {
                updateMessage(String.format("%s: %s", Keys.GUI_TEXT_PLEASE_WAIT_FILES_ARE_COPIED, filename));
                Files.copy(
                        new File(String.format("%s%s%s", selectedDirectory, File.separator, filename)).toPath(),
                        new File(String.format("%s%s", Config.importPath(), filename)).toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }
            updateMessage(Keys.GUI_TEXT_DONE);
            ProgressUpdate.finished();
            return null;
        } catch (IOException e) {
            throw new DentareportIOException(e);
        } catch (DentereportInterruptedException e) {
            return null;
        }
    }
}
