package dev.sergevas.iot.env.bh1750.adapter.out.i2c;

import dev.sergevas.iot.env.EnvDeviceAppConfig;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CBus;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.I2CInterface;
import io.quarkiverse.jef.java.embedded.framework.linux.i2c.SMBus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Bh1750AdapterTest {

    @Test
    void fromRawReadingsToLightIntensityTest() throws Exception {
        EnvDeviceAppConfig config = new EnvDeviceAppConfig();
        I2CBus i2CBus = mock(I2CBus.class);
        SMBus smBus = mock(SMBus.class);
        I2CInterface i2C = mock(I2CInterface.class);
        when(i2CBus.select(config.getBh1750ModuleAddress())).thenReturn(i2C);
        when(i2C.getSmBus()).thenReturn(smBus);
        Bh1750Adapter bh1750Adapter = new Bh1750Adapter(i2CBus, new EnvDeviceAppConfig());
        assertEquals(51030, bh1750Adapter.fromRawReadingsToLightIntensity(new byte[]{(byte) 0xef, (byte) 0x34}));
        assertEquals(1000, bh1750Adapter.fromRawReadingsToLightIntensity(new byte[]{(byte) 0x04, (byte) 0xb0}));
    }
}
