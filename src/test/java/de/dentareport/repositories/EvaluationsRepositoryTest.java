package de.dentareport.repositories;

import de.dentareport.utils.db.Db;
import de.dentareport.utils.db.DbConnection;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import static de.dentareport.utils.db.DbTestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class EvaluationsRepositoryTest {

    private Db db;
    private Connection connection;
    private EvaluationsRepository evaluationsRepository;
    @Mocked
    DbConnection mockDbConnection;

    @BeforeEach
    public void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        db = new Db(connection);
        new Expectations() {{
            DbConnection.db();
            result = db;
        }};
        this.evaluationsRepository = new EvaluationsRepository();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.close();
    }

    // TODO: Fix tests
//    @Test
//    public void it_rebuilds_table() throws Exception {
//        Set<String> columns = new HashSet<>(Arrays.asList("foo", "bar"));
//        evaluationsRepository.rebuildTable("test_table", columns);
//
//        assertThat(db.hasTable("test_table")).isTrue();
//        assertThat(tableHasColumn(connection, "test_table", "id")).isTrue();
//        assertThat(columnIsType(connection, "test_table", "id", "integer")).isTrue();
//        assertThat(columnIsPrimaryKey(connection, "test_table", "id")).isTrue();
//        assertThat(tableHasColumn(connection, "test_table", "foo")).isTrue();
//        assertThat(columnIsType(connection, "test_table", "foo", "text")).isTrue();
//        assertThat(tableHasColumn(connection, "test_table", "bar")).isTrue();
//        assertThat(columnIsType(connection, "test_table", "bar", "text")).isTrue();
//    }
}