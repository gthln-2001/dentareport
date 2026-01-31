package de.dentareport.imports.dampsoft.dampsoft_files;

import de.dentareport.Config;
import de.dentareport.utils.db.DbRow;
import de.dentareport.utils.dbf.DbfRow;
import mockit.Expectations;
import mockit.Mocked;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

// TODO: TEST?
public class DampsoftFileTest {

    private Foo foo;

    @BeforeEach
    public void setUp() {
        foo = new Foo();
    }

    // TODO: Fix tests

//    @Test
//    public void it_checks_if_a_file_exists(@Mocked File mockFile,
//                                           @Mocked Config mockConfig) {
//        Bar bar = new Bar();
//        new Expectations() {{
//            Config.importPath();
//            result = "some/path/";
//            new File("some/path/Bar.biz");
//            mockFile.exists();
//            result = false;
//        }};
//
//        assertThat(bar.isMissing()).isTrue();
//
//        new Expectations() {{
//            mockFile.exists();
//            result = true;
//            mockFile.isFile();
//            result = false;
//        }};
//
//        assertThat(bar.isMissing()).isTrue();
//
//        new Expectations() {{
//            mockFile.exists();
//            result = true;
//            mockFile.isFile();
//            result = true;
//        }};
//
//        assertThat(bar.isMissing()).isFalse();
//    }

    @Test
    public void it_throws_an_exception_if_method_filename_is_not_implemented() {
        assertThatExceptionOfType(NotImplementedException.class).isThrownBy(
                () -> foo.filename()
        );
    }

    @Test
    public void it_throws_an_exception_if_method_tablename_is_not_implemented() {
        assertThatExceptionOfType(NotImplementedException.class).isThrownBy(
                () -> foo.tablename()
        );
    }

    @Test
    public void it_throws_an_exception_if_method_columnsToImport_is_not_implemented() {
        assertThatExceptionOfType(NotImplementedException.class).isThrownBy(
                () -> foo.columnsToImport()
        );
    }

    @Test
    public void it_throws_an_exception_if_method_convert_is_not_implemented() {
        assertThatExceptionOfType(NotImplementedException.class).isThrownBy(
                () -> foo.convert(new DbfRow())
        );
    }

    @Test
    public void it_throws_an_exception_if_method_columnsToWrite_is_not_implemented() {
        assertThatExceptionOfType(NotImplementedException.class).isThrownBy(
                () -> foo.columnsToWrite()
        );
    }

    @Test
    public void it_throws_an_exception_if_method_isValidRow_is_not_implemented() {
        assertThatExceptionOfType(NotImplementedException.class).isThrownBy(
                () -> foo.isValidRow(new DbRow())
        );
    }
}

class Foo implements DampsoftFile {
    @Override
    public void importFile() {
    }
}

class Bar implements DampsoftFile {
    @Override
    public void importFile() {
    }

    @Override
    public String filename() {
        return "Bar.biz";
    }
}