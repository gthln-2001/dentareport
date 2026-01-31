package de.dentareport.imports.dampsoft;

import com.google.common.collect.ImmutableList;
import de.dentareport.imports.dampsoft.dampsoft_files.DampsoftFile;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: TEST?
public class DampsoftTest {

    private Dampsoft dampsoft;
    private List<DampsoftFile> files;
    @Mocked
    private DampsoftFile mockDampsoftFile1;
    @Mocked
    private DampsoftFile mockDampsoftFile2;

    @BeforeEach
    public void setUp() {
        files = testFiles();
        this.dampsoft = new Dampsoft(files);
    }

//    @Test
//    public void it_imports_data_for_each_file() {
//        dampsoft.importData();
//
//        new Verifications() {{
//            mockDampsoftFile1.importFile();
//            times = 1;
//            mockDampsoftFile2.importFile();
//            times = 1;
//        }};
//    }

    @Test
    public void it_only_accepts_an_object_of_type_list_because_the_order_of_the_filenames_is_important() {
        assertThat(files).isInstanceOf(List.class);
    }

    @Test
    public void it_gets_list_of_missing_dampsoft_files(@Mocked DampsoftFile mockFile1,
                                                       @Mocked DampsoftFile mockFile2,
                                                       @Mocked DampsoftFile mockFile3,
                                                       @Mocked DampsoftFile mockFile4) {
        new Expectations() {{
            mockFile1.isMissing();
            result = false;
            mockFile2.isMissing();
            result = true;
            mockFile2.filename();
            result = "XYZ";
            mockFile3.isMissing();
            result = true;
            mockFile3.filename();
            result = "ABC";
            mockFile4.isMissing();
            result = true;
            mockFile4.filename();
            result = "ABC";
        }};

        Dampsoft dampsoft = new Dampsoft(ImmutableList.of(mockFile1, mockFile2, mockFile3, mockFile4));
        List<String> result = dampsoft.missingFiles();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo("ABC");
        assertThat(result.get(1)).isEqualTo("XYZ");
    }

    @Test
    public void it_gets_list_of_required_dampsoft_files(@Mocked DampsoftFile mockFile1,
                                                        @Mocked DampsoftFile mockFile2,
                                                        @Mocked DampsoftFile mockFile3) {
        new Expectations() {{
            mockFile1.isRealFile();
            result = true;
            mockFile1.filename();
            result = "FOO.DBF";
            mockFile2.isRealFile();
            result = false;
            mockFile2.filename();
            times = 0;
            mockFile3.isRealFile();
            result = true;
            mockFile3.filename();
            result = "BAR.DBF";
        }};

        Dampsoft dampsoft = new Dampsoft(ImmutableList.of(mockFile1, mockFile2, mockFile3));
        List<String> result = dampsoft.requiredFiles();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo("BAR.DBF");
        assertThat(result.get(1)).isEqualTo("FOO.DBF");
    }


    private List<DampsoftFile> testFiles() {
        List<DampsoftFile> ret = new ArrayList<>();
        ret.add(mockDampsoftFile1);
        ret.add(mockDampsoftFile2);
        return ret;
    }
}
