package dev.sergevas.iot.env.adapter.out.system;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class CpuTempAdapterTest {

    @Inject
    CpuTempAdapter cpuTempAdapter;

    @Test
    void getCpuTemp() {
        if (System.getProperty("os.name").startsWith("Lin")) {
            assertTrue(cpuTempAdapter.getCpuTemp() > 0);
        } else {
            assertTrue(true);
        }
    }
}
