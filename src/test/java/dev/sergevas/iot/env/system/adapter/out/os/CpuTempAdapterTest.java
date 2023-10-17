package dev.sergevas.iot.env.system.adapter.out.os;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CpuTempAdapterTest {

    @Test
    void getCpuTemp() {
        if (System.getProperty("os.name").startsWith("Lin")) {
            assertTrue(new CpuTempAdapter().getCpuTemp() > 0);
        } else {
            assertTrue(true);
        }
    }
}
