package de.dentareport.gui;

import de.dentareport.License;
import de.dentareport.utils.Keys;

public class WindowTitleService {

    private final License license;

    public WindowTitleService(License license) {
        this.license = license;
    }

    public String title() {
        if (license.isDemo()) {
            return Keys.GUI_WINDOW_TITLE_MAIN + " - " + Keys.GUI_TEXT_DEMO_VERSION;
        }
        return Keys.GUI_WINDOW_TITLE_MAIN;
    }
}
