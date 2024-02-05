package dev.sergevas.iot.env.adapter.out.i2c.bh1750;

import dev.sergevas.iot.env.EnvDeviceAppConfig;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class Bh1750AdapterTest {
    @Test
    void fromRawReadingsToLightIntensityTest() {
        Bh1750Adapter bh1750Adapter = new Bh1750Adapter(mock(I2CBus.class), new EnvDeviceAppConfig());
        assertEquals(51030, bh1750Adapter.fromRawReadingsToLightIntensity(new byte[]{(byte) 0xef, (byte) 0x34}));
        assertEquals(1000, bh1750Adapter.fromRawReadingsToLightIntensity(new byte[]{(byte) 0x04, (byte) 0xb0}));
    }
}
