package dev.sergevas.iot.env.application.service.health;

import dev.sergevas.iot.env.application.port.in.health.DiskSpaceUseCase;
import dev.sergevas.iot.env.application.port.out.health.DiskSpaceFetcher;
import dev.sergevas.iot.env.domain.health.DiskSpace;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DiskSpaceService implements DiskSpaceUseCase {

    @ConfigProperty(name = "healthcheck.diskSpace.thresholdPercent", defaultValue = "99.999")
    double diskSpaceThresholdPercent;

    @Inject
    DiskSpaceFetcher diskSpaceFetcher;

    @Override
    public DiskSpace getDiskSpace() {
        return diskSpaceFetcher.getDiskSpace();
    }

    public boolean isThresholdReached(DiskSpace diskSpace) {
        long threshold = (long) ((diskSpaceThresholdPercent / 100) * diskSpace.totalInBytes());
        return threshold >= diskSpace.usedInBytes();
    }
}
