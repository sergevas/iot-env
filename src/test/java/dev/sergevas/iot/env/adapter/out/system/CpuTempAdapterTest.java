package dev.sergevas.iot.env.adapter.out.system;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CpuTempAdapterTest {
    @Test
    void getCpuTemp() {
        if (System.getProperty("os.name").startsWith("Lin")) {
            assertNotEquals(0, new CpuTempAdapter().getCpuTemp());
        } else {
            assertTrue(true);
        }
    }
}
