package dev.sergevas.iot.env.application.port.in.bmp180;

import dev.sergevas.iot.env.domain.SensorReadingsItemType;
import dev.sergevas.iot.env.domain.SensorReadingsType;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Pressure;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Readings;
import dev.sergevas.iot.env.domain.bmp180.Bmp180Temperature;

import static dev.sergevas.iot.env.domain.SensorName.BMP180;
import static dev.sergevas.iot.env.domain.SensorType.PRESS;
import static dev.sergevas.iot.env.domain.SensorType.TEMP;
import static dev.sergevas.iot.env.domain.UnitsOfMeasurement.*;
import static java.lang.String.valueOf;
import static java.time.OffsetDateTime.now;
import static java.time.ZoneOffset.UTC;


public class ToBmp180SensorReadingsTypeMapper {

    public static SensorReadingsType toBmp180SensorReadingsType(Bmp180Readings readings) {
        return new SensorReadingsType().addSReadingsItem(new SensorReadingsItemType()
                        .sId(readings.chipId())
                        .sType(TEMP.name())
                        .sUnits(CELSIUS.getUnits())
                        .sName(BMP180.getName())
                        .sTimestamp(now(UTC))
                        .sData(valueOf(readings.temperature().degrees())))
                .addSReadingsItem(new SensorReadingsItemType()
                        .sId(readings.chipId())
                        .sType(PRESS.name())
                        .sUnits(PA.getUnits())
                        .sName(BMP180.getName())
                        .sTimestamp(now(UTC))
                        .sData(valueOf(readings.pressure().pa())))
                .addSReadingsItem(new SensorReadingsItemType()
                        .sId(readings.chipId())
                        .sType(PRESS.name())
                        .sUnits(MM_HG.getUnits())
                        .sName(BMP180.getName())
                        .sTimestamp(now(UTC))
                        .sData(valueOf(readings.pressure().mmHg())));
    }

    public static SensorReadingsItemType toBmp180TempSensorReadingsItemType(Bmp180Temperature temperature, String chipId) {
        return new SensorReadingsItemType()
                .sId(chipId)
                .sType(TEMP.name())
                .sUnits(CELSIUS.getUnits())
                .sName(BMP180.getName())
                .sTimestamp(now(UTC))
                .sData(valueOf(temperature.degrees()));
    }

    public static SensorReadingsType toBmp180PressSensorReadingsItemType(Bmp180Pressure pressure, String chipId) {
        return new SensorReadingsType()
                .addSReadingsItem(new SensorReadingsItemType()
                        .sId(chipId)
                        .sType(PRESS.name())
                        .sUnits(PA.getUnits())
                        .sName(BMP180.getName())
                        .sTimestamp(now(UTC))
                        .sData(valueOf(pressure.pa())))
                .addSReadingsItem(new SensorReadingsItemType()
                        .sId(chipId)
                        .sType(PRESS.name())
                        .sUnits(MM_HG.getUnits())
                        .sName(BMP180.getName())
                        .sTimestamp(now(UTC))
                        .sData(valueOf(pressure.mmHg())));
    }
}
