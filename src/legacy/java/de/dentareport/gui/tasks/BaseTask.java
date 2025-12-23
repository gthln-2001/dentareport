package de.dentareport.gui.tasks;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.evaluations.Evaluation7;
import de.dentareport.evaluations.Evaluation9;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTask extends Task<Void> {

    @Override
    public void updateProgress(double workDone, double max) {
        super.updateProgress(workDone, max);
    }

    @Override
    public void updateMessage(String message) {
        super.updateMessage(message);
    }

    protected List<Class<? extends Evaluation>> availableEvaluations() {
        List<Class<? extends Evaluation>> ret = new ArrayList<>();
        ret.add(Evaluation7.class);
        ret.add(Evaluation9.class);
        return ret;
    }
}
