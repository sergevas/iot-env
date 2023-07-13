package dev.sergevas.iot.growlabv1.hardware.boundary;

import dev.sergevas.iot.env.system.boundary.CpuTempProcessBuilder;
import dev.sergevas.iot.env.system.boundary.SystemInfoAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SystemInfoAdapterTest {

    @Test
    void getCpuTemp() {
        if (System.getProperty("os.name").startsWith("Lin")) {
            assertNotNull(new SystemInfoAdapter(new CpuTempProcessBuilder()).getCpuTemp());
        } else {
            assertTrue(true);
        }
    }
}
