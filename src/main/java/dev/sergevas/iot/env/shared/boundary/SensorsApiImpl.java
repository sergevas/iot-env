package dev.sergevas.iot.env.shared.boundary;

import dev.sergevas.iot.env.bh1750.control.Bh1750Service;
import dev.sergevas.iot.env.shared.entity.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.entity.SensorReadingsType;
import dev.sergevas.iot.env.system.control.SystemInfoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SensorsApiImpl implements SensorsApi {

    private final SystemInfoService systemInfoService;
    private final Bh1750Service bh1750Service;

    @Inject
    public SensorsApiImpl(SystemInfoService systemInfoService,
                          Bh1750Service bh1750Service) {
        this.systemInfoService = systemInfoService;
        this.bh1750Service = bh1750Service;
    }

    @Override
    public SensorReadingsItemType getBH1750AmbientLightIntensity() {
        return bh1750Service.getSensorReadingsItemTypeForBh1750();
    }

    @Override
    public SensorReadingsType getBME280THP() {
        return null;
    }

    @Override
    public SensorReadingsItemType getBMP180TempPress() {
        return null;
    }

    @Override
    public SensorReadingsItemType getSHT3xHumidTemp() {
        return null;
    }

    @Override
    public SensorReadingsType getSystemData() {
        return systemInfoService.getSystemInfo();
    }
}
