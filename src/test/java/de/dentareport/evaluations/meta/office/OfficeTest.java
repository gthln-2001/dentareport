package de.dentareport.evaluations.meta.office;

import com.google.common.collect.ImmutableList;
import de.dentareport.evaluations.office.Item;
import de.dentareport.evaluations.office.Items;
import de.dentareport.evaluations.office.Office;
import de.dentareport.utils.Keys;
import de.dentareport.utils.db.Db;
import de.dentareport.utils.db.DbConnection;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.dentareport.TestHelper.dbInMemory;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class OfficeTest {

    private Db db;
    private Office office;
    @Mocked
    DbConnection mockDbConnection;

    @BeforeEach
    public void setUp() throws Exception {
        db = dbInMemory();
        new Expectations() {{
            DbConnection.db();
            result = db;
        }};
        this.office = new Office();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void it_rebuilds_database(@Mocked Items mockItems) {
        new Expectations() {{
            mockItems.items();
            result = ImmutableList.of();
        }};

        assertThat(db.hasTable(Keys.DB_TABLENAME_OFFICE_EVALUATION_AVERAGES)).isFalse();
        assertThat(db.hasTable(Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP)).isFalse();

        office.evaluate();

        assertThat(db.hasTable(Keys.DB_TABLENAME_OFFICE_EVALUATION_AVERAGES)).isTrue();
        assertThat(db.hasTable(Keys.DB_TABLENAME_OFFICE_EVALUATION_COUNTS_PER_AGE_GROUP)).isTrue();
    }

    @Test
    public void it_evaluates_each_item(@Mocked Items mockItems,
                                       @Mocked Item mockItem1,
                                       @Mocked Item mockItem2) {

        new Expectations() {{
            mockItems.items();
            result = ImmutableList.of(
                    mockItem1,
                    mockItem2
            );
        }};

        office.evaluate();

        new Verifications() {{
            mockItem1.evaluate();
            times = 1;
            mockItem2.evaluate();
            times = 1;
        }};
    }
}