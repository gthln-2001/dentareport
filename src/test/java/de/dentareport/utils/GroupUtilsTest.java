package de.dentareport.utils;

import de.dentareport.utils.db.Db;
import de.dentareport.utils.db.DbConnection;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class GroupUtilsTest {

    private Connection connection;
    private Db db;
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
    }

    @AfterEach
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void it_gets_all_occurring_values_of_column() throws Exception {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE foo_table (valueColumn text, groupColumn text)");
        statement.executeUpdate("INSERT INTO foo_table (valueColumn, groupColumn) VALUES ('4', 'a')");
        statement.executeUpdate("INSERT INTO foo_table (valueColumn, groupColumn) VALUES ('1', 'b')");
        statement.executeUpdate("INSERT INTO foo_table (valueColumn, groupColumn) VALUES ('2', 'b')");
        statement.executeUpdate("INSERT INTO foo_table (valueColumn, groupColumn) VALUES ('1', 'b')");
        statement.executeUpdate("INSERT INTO foo_table (valueColumn, groupColumn) VALUES ('8', 'c')");
        statement.executeUpdate("INSERT INTO foo_table (valueColumn, groupColumn) VALUES ('5', 'a')");
        statement.executeUpdate("INSERT INTO foo_table (valueColumn, groupColumn) VALUES ('12', '" + Keys.NO_DATA + "')");

        assertThat(GroupUtils.valuesForColumn("foo_table", "groupColumn", "valueColumn")).isEqualTo("b; a; c");
    }
}