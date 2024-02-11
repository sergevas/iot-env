package dev.sergevas.iot.env.application.port.in.health;

import dev.sergevas.iot.env.domain.health.DiskSpace;

public interface DiskSpaceUseCase {

    DiskSpace getDiskSpace();

    boolean isThresholdReached(DiskSpace diskSpace);
}
