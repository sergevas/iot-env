package dev.sergevas.iot.env.adapter.in.http.health;

import dev.sergevas.iot.env.application.port.in.health.SystemInfoUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

//@Path("sensors/system")
@ApplicationScoped
@Liveness
public class SystemInfoHealthCheck implements HealthCheck {

    private static final String HEALTH_CHECK_NAME = "systemInfo";
    private static final String CPU_TEMP = "cpuTemp";
    private final SystemInfoUseCase systemInfoUseCase;

    public SystemInfoHealthCheck(SystemInfoUseCase systemInfoUseCase) {
        this.systemInfoUseCase = systemInfoUseCase;
    }

    @Override
    public HealthCheckResponse call() {
        var systemInfo = systemInfoUseCase.getSystemInfo();
        return HealthCheckResponse
                .named(HEALTH_CHECK_NAME)
                .up()
                .withData(CPU_TEMP, String.valueOf(systemInfo.getCpuTemp()))
                .build();
    }



    /*@GET
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
    }*/
}
