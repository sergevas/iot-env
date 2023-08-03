package dev.sergevas.iot.env.system.control;

import dev.sergevas.iot.env.shared.entity.SensorName;
import dev.sergevas.iot.env.shared.entity.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.entity.SensorReadingsType;
import dev.sergevas.iot.env.shared.entity.SensorType;
import dev.sergevas.iot.env.system.boundary.SystemInfoAdapter;
import io.quarkus.logging.Log;
import jakarta.inject.Singleton;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Singleton
public class SystemInfoService {
    private final SystemInfoAdapter systemInfoAdapter;

    public SystemInfoService(SystemInfoAdapter systemInfoAdapter) {
        this.systemInfoAdapter = systemInfoAdapter;
    }

    public SensorReadingsType getSystemInfo() {
        SensorReadingsType sensorReadingsType = new SensorReadingsType()
                .addSReadingsItem(new SensorReadingsItemType()
                        .sType(SensorType.CPU_TEMP.name())
                        .sName(SensorName.ORANGE_PI_ZERO.getName())
                        .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                        .sData(systemInfoAdapter.getCpuTemp()));
        Log.debugf("%s.getSystemInfo() %s", SystemInfoService.class, sensorReadingsType);
        return sensorReadingsType;
    }
}
