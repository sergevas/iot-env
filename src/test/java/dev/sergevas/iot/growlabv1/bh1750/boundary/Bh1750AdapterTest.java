package dev.sergevas.iot.growlabv1.bh1750.boundary;

import dev.sergevas.iot.env.bh1750.boundary.Bh1750Adapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Bh1750AdapterTest {

    @Test
    void fromRawReadingsToLightIntensityTest() {
        assertEquals(51030, new Bh1750Adapter(null, null)
                .fromRawReadingsToLightIntensity(new byte[]{(byte) 0xef, (byte) 0x34}));
        assertEquals(1000, new Bh1750Adapter(null, null)
                .fromRawReadingsToLightIntensity(new byte[]{(byte) 0x04, (byte) 0xb0}));
    }
}
