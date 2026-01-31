package de.dentareport.utils;

import de.dentareport.License;
import de.dentareport.Metadata;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.jupiter.api.Test;

// TODO: TEST?
public class PreStarterTest {

    @Test
    public void it_runs_pre_start_tasks(@Mocked Metadata mockMetadata,
                                        @Mocked License mockLicense) {
        PreStarter preStarter = new PreStarter();
        preStarter.runPreStartTasks();

        new Verifications() {{
            Metadata.init();
            times = 1;
            mockLicense.check();
            times = 1;
        }};
    }
}