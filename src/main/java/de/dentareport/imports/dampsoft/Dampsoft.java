package de.dentareport.imports.dampsoft;

//import de.dentareport.gui.ProgressUpdate;

import de.dentareport.gui.util.FileProgressListener;
import de.dentareport.gui.util.ProgressListener;
import de.dentareport.imports.dampsoft.dampsoft_files.*;
import de.dentareport.utils.Keys;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: TEST?
public class Dampsoft {

    private final List<DampsoftFile> files;

    public Dampsoft(List<DampsoftFile> files) {
        this.files = files;
    }

    public Dampsoft() {
        this(filesToImport());
    }

    private static List<DampsoftFile> filesToImport() {
        List<DampsoftFile> ret = new ArrayList<>();
        ret.add(new PatkuerzDbf());
        ret.add(new BefundDbf01());
        ret.add(new BefundDbfPa());
        ret.add(new HkpplanDbf());
        ret.add(new BefundDbfHkp());
        ret.add(new PatinfoDbf());
        ret.add(new HkplstDbf());
        ret.add(new FirstAndLastVisit());
        ret.add(new PatientDbf());
        ret.add(new ZnotizDbf());
        ret.add(new EndostmpDbf());
//        ret.add(new PatviewDbf()); // We do not need this file right now, maybe later if we want to work with xrays...
        return ret;
    }

    public List<DampsoftFile> files() {
        return files;
    }

    public List<String> missingFiles() {
        return files.stream()
                .filter(DampsoftFile::isMissing)
                .map(DampsoftFile::filename)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public void importData(ProgressListener listener, FileProgressListener fileListener) {
        int total = files.size();
        int count = 1;
        for (DampsoftFile dampsoftFile : files) {
            int percent = count * 10;
            listener.onProgress(percent, String.format(Keys.GUI_TEXT_STEP_X_OF_Y, count++, total));
            dampsoftFile.importFile(fileListener);
        }

    }

    public List<String> requiredFiles() {
        return files.stream()
                .filter(DampsoftFile::isRealFile)
                .map(DampsoftFile::filename)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}