package dev.sergevas.iot.env.application.port.in.sht3x;

import dev.sergevas.iot.env.domain.SensorReadingsItemType;
import dev.sergevas.iot.env.domain.SensorReadingsType;
import dev.sergevas.iot.env.domain.sht3x.Sht3xReadings;

import static dev.sergevas.iot.env.domain.SensorName.SHT3x;
import static dev.sergevas.iot.env.domain.SensorType.HUMID;
import static dev.sergevas.iot.env.domain.SensorType.TEMP;
import static dev.sergevas.iot.env.domain.UnitsOfMeasurement.CELSIUS;
import static dev.sergevas.iot.env.domain.UnitsOfMeasurement.PERCENT;
import static java.lang.String.valueOf;
import static java.time.OffsetDateTime.now;
import static java.time.ZoneOffset.UTC;


public class ToSht3xSensorReadingsTypeMapper {

    public static SensorReadingsType toSht3xSensorReadingsType(Sht3xReadings readings) {
        return new SensorReadingsType().addSReadingsItem(
                        new SensorReadingsItemType()
                                .sType(TEMP.name())
                                .sUnits(CELSIUS.getUnits())
                                .sName(SHT3x.getName())
                                .sTimestamp(now(UTC))
                                .sData(valueOf(readings.temperature())))
                .addSReadingsItem(new SensorReadingsItemType()
                        .sType(HUMID.name())
                        .sUnits(PERCENT.getUnits())
                        .sName(SHT3x.getName())
                        .sTimestamp(now(UTC))
                        .sData(valueOf(readings.humidity())));
    }
}
