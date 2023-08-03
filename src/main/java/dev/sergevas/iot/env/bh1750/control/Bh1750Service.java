package dev.sergevas.iot.env.bh1750.control;

import dev.sergevas.iot.env.bh1750.boundary.Bh1750Adapter;
import dev.sergevas.iot.env.shared.entity.SensorName;
import dev.sergevas.iot.env.shared.entity.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.entity.SensorType;
import io.quarkus.logging.Log;
import jakarta.inject.Singleton;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Singleton
public class Bh1750Service {
    private final Bh1750Adapter bh1750Adapter;

    public Bh1750Service(Bh1750Adapter bh1750Adapter) {
        this.bh1750Adapter = bh1750Adapter;
    }

    public SensorReadingsItemType getSensorReadingsItemTypeForBh1750() {
        double lightIntensity = bh1750Adapter.getLightIntensity();
        SensorReadingsItemType sensorReadings = new SensorReadingsItemType()
                .sType(SensorType.LIGHT.name())
                .sName(SensorName.BH1750.getName())
                .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .sData(String.valueOf(lightIntensity));
        Log.debugf("%s.getSensorReadingsItemTypeForBh1750() %s", Bh1750Service.class, sensorReadings);
        return sensorReadings;
    }
}
