package dev.sergevas.iot.env.application.port.out.health;

import dev.sergevas.iot.env.domain.health.DiskSpace;

public interface DiskSpaceFetcher {
    DiskSpace getDiskSpace();
}
