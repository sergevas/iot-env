package dev.sergevas.iot.env.bh1750.control;

import dev.sergevas.iot.env.bh1750.boundary.Bh1750Adapter;
import dev.sergevas.iot.env.shared.entity.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.entity.SensorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Bh1750Service {
    private static final Logger LOG = LoggerFactory.getLogger(Bh1750Service.class);

    private final Bh1750Adapter bh1750Adapter;

    public Bh1750Service(Bh1750Adapter bh1750Adapter) {
        this.bh1750Adapter = bh1750Adapter;
    }

    public SensorReadingsItemType getSensorReadingsItemTypeForBh1750() {
        double lightIntensity = bh1750Adapter.getLightIntensity();
        SensorReadingsItemType sensorReadings = new SensorReadingsItemType()
                .sType(SensorType.LIGHT.name())
                .sName("BH1750")
                .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .sData(String.valueOf(lightIntensity));
        LOG.debug("{}.getSensorReadingsItemTypeForBh1750() lightIntensity=[{}]", Bh1750Service.class, sensorReadings);
        return sensorReadings;
    }
}
