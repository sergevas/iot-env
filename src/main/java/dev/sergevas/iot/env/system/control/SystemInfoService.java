package dev.sergevas.iot.env.system.control;

import dev.sergevas.iot.env.shared.entity.SensorName;
import dev.sergevas.iot.env.shared.entity.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.entity.SensorReadingsType;
import dev.sergevas.iot.env.shared.entity.SensorType;
import dev.sergevas.iot.env.system.boundary.SystemInfoAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class SystemInfoService {
    private static final Logger LOG = LoggerFactory.getLogger(SystemInfoService.class);
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
        LOG.debug("{}.getSystemInfo() {}", SystemInfoService.class, sensorReadingsType);
        return sensorReadingsType;
    }
}
