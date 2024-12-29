package dev.sergevas.iot.env.domain.sht3x;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusRegisterTest {

    @Test
    void fromRawData() {
        var expected = new StatusRegister(
                (byte) 0,
                (byte) 0,
                (byte) 1,
                (byte) 0,
                (byte) 1,
                (byte) 1,
                (byte) 0
        );
        var actual = StatusRegister.fromRawData(new int[]{0b01101010, 0b10110000});
        assertEquals(expected, actual);
    }

    @Test
    void testStringValues() {
        var sr = new StatusRegister(
                (byte) 0,
                (byte) 0,
                (byte) 0,
                (byte) 0,
                (byte) 0,
                (byte) 0,
                (byte) 0);
        assertEquals("LAST_WRITE_CORRECT", sr.writeDataChecksumStatus());
        assertEquals("LAST_COMMAND_SUCCESS", sr.commandStatus());
        assertEquals("NO_RESET", sr.systemResetDetected());
        assertEquals("NO_ALERT", sr.tTrackingAlert());
        assertEquals("NO_ALERT", sr.rhTrackingAlert());
        assertEquals("OFF", sr.heaterStatus());
        assertEquals("NO_PENDING_ALERT", sr.alertPendingStatus());

        sr = new StatusRegister(
                (byte) 1,
                (byte) 1,
                (byte) 1,
                (byte) 1,
                (byte) 1,
                (byte) 1,
                (byte) 1);
        assertEquals("LAST_WRITE_FAILED", sr.writeDataChecksumStatus());
        assertEquals("LAST_COMMAND_NOT_PROCESSED", sr.commandStatus());
        assertEquals("RESET", sr.systemResetDetected());
        assertEquals("ALERT", sr.tTrackingAlert());
        assertEquals("ALERT", sr.rhTrackingAlert());
        assertEquals("ON", sr.heaterStatus());
        assertEquals("AT_LEAST_ONE_PENDING_ALERT", sr.alertPendingStatus());
    }
}
