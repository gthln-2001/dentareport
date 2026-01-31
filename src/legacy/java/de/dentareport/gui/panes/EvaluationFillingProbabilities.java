package de.dentareport.gui.panes;

import de.dentareport.gui.Gui;
import de.dentareport.utils.Keys;

import java.util.Map;

// TODO: TEST?
public class EvaluationFillingProbabilities extends ContentPaneEvaluationProbabilities {

    public EvaluationFillingProbabilities(Gui gui, Map<String, String> options) {
        super(gui, options);
    }

    @Override
    protected String guiText() {
        return Keys.GUI_TEXT_FILLINGS;
    }

    @Override
    protected String keyProbabilitiesDisplayView() {
        return Keys.GUI_VIEW_EVALUATION_FILLING_PROBABILITIES_DISPLAY;
    }

    @Override
    protected String parentPane() {
        return Keys.GUI_VIEW_EVALUATION_FILLING;
    }
}
