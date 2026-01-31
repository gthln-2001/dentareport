package de.dentareport.gui.panes;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.evaluations.Evaluation9;
import de.dentareport.gui.Gui;
import de.dentareport.utils.Keys;

import java.util.Map;

// TODO: TEST?
public class EvaluationTelescopicCrown extends ContentPaneEvaluation {

    private Gui gui;

    public EvaluationTelescopicCrown(Gui gui, Map<String, String> options) {
        this.gui = gui;
    }

    protected Gui getGui() {
        return gui;
    }

    protected String guiText() {
        return Keys.GUI_TEXT_TELESCOPIC_CROWNS;
    }

    protected String contentPaneViewGeneral() {
        return Keys.GUI_VIEW_EVALUATION_TELESCOPIC_CROWN_GENERAL;
    }

    protected String contentPaneViewProbabilities() {
        return Keys.GUI_VIEW_EVALUATION_TELESCOPIC_CROWN_PROBABILITIES;
    }

    protected String metadataKey() {
        return Keys.METADATA_KEY_VALID_EVALUATION_TELESCOPIC_CROWN;
    }

    protected String errorNoValidEvaluation() {
        return Keys.GUI_TEXT_ERROR_NO_VALID_EVALUATION_TELESCOPIC_CROWN;
    }

    protected Class<? extends Evaluation> evaluationClass() {
        return Evaluation9.class;
    }
}