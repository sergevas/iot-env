package dev.sergevas.iot.growlabv1.hardware.boundary;

import dev.sergevas.iot.env.hardware.boundary.SystemInfoAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemInfoAdapterTest {

    @Test
    void getCpuTemp() {
        if (System.getProperty("os.name").startsWith("Lin")) {
            assertNotNull(SystemInfoAdapter.create().getCpuTemp());
        } else {
            assertTrue(true);
        }
    }
}