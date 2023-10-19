package dev.sergevas.iot.env.system.adapter.in.http;

import dev.sergevas.iot.env.shared.domain.SensorName;
import dev.sergevas.iot.env.shared.domain.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.domain.SensorReadingsType;
import dev.sergevas.iot.env.shared.domain.SensorType;
import dev.sergevas.iot.env.system.application.port.in.SystemInfoUseCase;
import dev.sergevas.iot.env.system.domain.SystemInfo;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Path("sensors/system")
public class SystemInfoResource {

    private final SystemInfoUseCase systemInfoUseCase;

    @Inject
    public SystemInfoResource(SystemInfoUseCase systemInfoUseCase) {
        this.systemInfoUseCase = systemInfoUseCase;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReadingsType getSystemData() {
        SystemInfo systemInfo = systemInfoUseCase.getSystemInfo();
        SensorReadingsType sensorReadingsType = new SensorReadingsType()
                .addSReadingsItem(new SensorReadingsItemType()
                        .sType(SensorType.CPU_TEMP.name())
                        .sName(SensorName.ORANGE_PI_ZERO.getName())
                        .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                        .sData(String.valueOf(systemInfo.getCpuTemp())));
        Log.info(sensorReadingsType);
        return sensorReadingsType;
    }
}