package de.dentareport.utils;

import de.dentareport.License;
import de.dentareport.Metadata;

public class PreStarter {

    private final License license;

    public PreStarter() {
        this.license = new License();
    }

    public void runPreStartTasks() {
        Metadata.init();
        license.check();
    }
}
