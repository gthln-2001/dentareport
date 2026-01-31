package de.dentareport.gui.panes;

import com.google.common.collect.ImmutableMap;
import de.dentareport.exceptions.DentareportIllegalAccessException;
import de.dentareport.gui.Gui;
import de.dentareport.utils.Keys;
import javafx.scene.layout.Pane;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

// TODO: TEST?
public class ContentPaneFactory {

    private Gui gui;
    private Map<String, Class<? extends ContentPane>> panes =
            ImmutableMap.<String, Class<? extends ContentPane>>builder()
                    .put(Keys.GUI_VIEW_COPY_FILES,
                         CopyFiles.class)
                    .put(Keys.GUI_VIEW_COPY_FILES_SELECT_FOLDER,
                         CopyFilesSelectFolder.class)
                    .put(Keys.GUI_VIEW_EVALUATION,
                         Evaluation.class)
                    .put(Keys.GUI_VIEW_GENERAL_PATIENT_INFORMATION,
                         GeneralPatientInformation.class)
                    .put(Keys.GUI_VIEW_EVALUATION_FILLING,
                         EvaluationFilling.class)
                    .put(Keys.GUI_VIEW_EVALUATION_FILLING_GENERAL,
                         EvaluationFillingGeneral.class)
                    .put(Keys.GUI_VIEW_EVALUATION_FILLING_PROBABILITIES,
                         EvaluationFillingProbabilities.class)
                    .put(Keys.GUI_VIEW_EVALUATION_FILLING_PROBABILITIES_DISPLAY,
                         EvaluationFillingProbabilitiesDisplay.class)
                    .put(Keys.GUI_VIEW_EVALUATION_TELESCOPIC_CROWN,
                         EvaluationTelescopicCrown.class)
                    .put(Keys.GUI_VIEW_EVALUATION_TELESCOPIC_CROWN_GENERAL,
                         EvaluationTelescopicCrownGeneral.class)
                    .put(Keys.GUI_VIEW_EVALUATION_TELESCOPIC_CROWN_PROBABILITIES,
                         EvaluationTelescopicCrownProbabilities.class)
                    .put(Keys.GUI_VIEW_EVALUATION_TELESCOPIC_CROWN_PROBABILITIES_DISPLAY,
                         EvaluationTelescopicCrownProbabilitiesDisplay.class)
                    .put(Keys.GUI_VIEW_IMPORT,
                         Import.class)
                    .build();

    public ContentPaneFactory(Gui gui) {
        this.gui = gui;
    }

    public Pane create(Map<String, String> options) {
        try {
            return pane(options)
                    .getConstructor(Gui.class, Map.class)
                    .newInstance(gui, options)
                    .pane();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new DentareportIllegalAccessException(e);
        }
    }

    private Class<? extends ContentPane> pane(Map<String, String> options) {
        return panes.getOrDefault(
                options.getOrDefault("view", ""),
                Start.class);
    }
}

