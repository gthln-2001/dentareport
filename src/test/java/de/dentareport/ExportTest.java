package de.dentareport;

import de.dentareport.evaluations.Evaluation;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.DbConnection;
import de.dentareport.utils.xls.Xls;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.Test;

// TODO: TEST?
public class ExportTest {

    @Mocked
    DbConnection mockDbConnection;
    @Mocked
    Evaluation mockEvaluation;
    @Mocked
    Glossary mockGlossary;
    @Mocked
    Translate mockTranslate;
    @Mocked
    Xls mockXls;

    @Test
    public void it_creates_xls_object() {
        new Expectations() {{
            new Xls();
            times = 1;
        }};

        Export export = new Export(mockEvaluation);
        export.export();
    }

    @Test
    public void it_creates_xls_sheets_and_writes_file() {
        Export export = new Export(mockEvaluation);
        export.export();

        new Verifications() {{
            mockXls.addSheet(Keys.XLS_EVALUATION);
            mockXls.addSheet(Keys.XLS_DOCUMENTATION);
            mockXls.addSheet(Keys.XLS_GLOSSARY);
            mockXls.write(export.filename());
        }};
    }
}