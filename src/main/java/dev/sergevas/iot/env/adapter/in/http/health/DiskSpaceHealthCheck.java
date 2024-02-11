package dev.sergevas.iot.env.adapter.in.http.health;

import dev.sergevas.iot.env.application.port.in.health.DiskSpaceUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@ApplicationScoped
@Liveness
public class DiskSpaceHealthCheck implements HealthCheck {

    private static final String HEALTH_CHECK_NAME = "diskSpace";

    private final DiskSpaceUseCase diskSpaceUseCase;

    public DiskSpaceHealthCheck(DiskSpaceUseCase diskSpaceUseCase) {
        this.diskSpaceUseCase = diskSpaceUseCase;
    }

    @Override
    public HealthCheckResponse call() {
        var diskSpace = diskSpaceUseCase.getDiskSpace();
        return HealthCheckResponse
                .named(HEALTH_CHECK_NAME)
                .status(diskSpaceUseCase.isThresholdReached(diskSpace))
                .withData("freeBytes", diskSpace.diskFreeInBytes())
                .withData("free", diskSpace.free())
                .withData("percentFree", diskSpace.percentFree())
                .withData("totalBytes", diskSpace.totalInBytes())
                .withData("total", diskSpace.total())
                .build();
    }
}
