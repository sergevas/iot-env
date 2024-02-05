package dev.sergevas.iot.env.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorReadingsItemTypeTest {
    @Test
    void toJson() {
        var json = new SensorReadingsItemType()
                .sType(SensorType.LIGHT.name())
                .sName(SensorName.BH1750.getName())
                .sTimestamp(OffsetDateTime.of(LocalDateTime.of(2021, 12, 13, 14, 15, 16), ZoneOffset.UTC))
                .sData(String.valueOf(1234.56))
                .toJson();
        assertEquals("""
                {"sType":"LIGHT","sId":null,"sName":"BH1750","sData":"1234.56","sTimestamp":"2021-12-13T14:15:16Z"}""", json);
    }
}
