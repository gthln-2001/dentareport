package de.dentareport.gui.tables;

import de.dentareport.Translate;

public abstract class Table {

    private final Translate translate;

    public Table() {
        this.translate = new Translate();
    }

    protected String translate(String value) {
        return translate.translate(value);
    }

    protected String translate(String value, String evaluationId) {
        return translate.translate(value, evaluationId);
    }
}
