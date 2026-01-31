package de.dentareport.gui.panes;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.evaluations.Evaluation7;
import de.dentareport.gui.Gui;
import de.dentareport.utils.Keys;

import java.util.Map;

// TODO: TEST?
public class EvaluationFilling extends ContentPaneEvaluation {

    private Gui gui;

    public EvaluationFilling(Gui gui, Map<String, String> options) {
        this.gui = gui;
    }

    protected Gui getGui() {
        return gui;
    }

    protected String guiText() {
        return Keys.GUI_TEXT_FILLINGS;
    }

    protected String contentPaneViewGeneral() {
        return Keys.GUI_VIEW_EVALUATION_FILLING_GENERAL;
    }

    protected String contentPaneViewProbabilities() {
        return Keys.GUI_VIEW_EVALUATION_FILLING_PROBABILITIES;
    }

    protected String metadataKey() {
        return Keys.METADATA_KEY_VALID_EVALUATION_FILLING;
    }

    protected String errorNoValidEvaluation() {
        return Keys.GUI_TEXT_ERROR_NO_VALID_EVALUATION_FILLING;
    }

    protected Class<? extends Evaluation> evaluationClass() {
        return Evaluation7.class;
    }
}