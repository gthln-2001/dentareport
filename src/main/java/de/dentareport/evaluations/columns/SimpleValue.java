package de.dentareport.evaluations.columns;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.Evaluation;
import de.dentareport.exceptions.DentareportIllegalAccessException;
import de.dentareport.models.CaseData;
import de.dentareport.models.RawData;
import org.apache.commons.lang3.text.WordUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static de.dentareport.utils.Keys.OF;
import static de.dentareport.utils.Keys.ORIGINAL_NAME;

public abstract class SimpleValue extends EvaluationColumn {

    public SimpleValue(Evaluation evaluation, Map<String, String> options) {
        super(evaluation, options);
    }

    @Override
    public CaseData evaluate(RawData rawData, CaseData caseData) {
        caseData.setString(options.get(ORIGINAL_NAME), getValue(caseData));
        return caseData;
    }

    @Override
    protected List<String> requiredColumns() {
        return ImmutableList.of(
                options.get(OF)
        );
    }

    private String getValue(CaseData caseData) {
        try {
            Method method = method(caseData);
            return (String) method.invoke(caseData.event(options.get(OF)));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new DentareportIllegalAccessException(e);
        }
    }

    private Method method(CaseData caseData) throws NoSuchMethodException {
        return caseData.event(options.get(OF))
                       .getClass()
                       .getMethod(methodname());
    }

    private String methodname() {
        return WordUtils.uncapitalize(this.getClass().getSimpleName());
    }
}
