package dev.sergevas.iot.env.application.port.in.bh1750;

import dev.sergevas.iot.env.domain.Bh1750Readings;
import dev.sergevas.iot.env.domain.SensorName;
import dev.sergevas.iot.env.domain.SensorReadingsItemType;
import dev.sergevas.iot.env.domain.SensorType;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static dev.sergevas.iot.env.domain.UnitsOfMeasurement.LUX;

public class ToBh1750SensorReadingsTypeMapper {

    public static SensorReadingsItemType toBh1750SensorReadingsType(Bh1750Readings readings) {
        return new SensorReadingsItemType()
                .sType(SensorType.LIGHT.name())
                .sUnits(LUX.getUnits())
                .sName(SensorName.BH1750.getName())
                .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .sData(String.valueOf(readings.getLightIntensity()));
    }
}
