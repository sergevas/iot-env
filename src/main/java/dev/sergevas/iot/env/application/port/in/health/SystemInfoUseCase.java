package dev.sergevas.iot.env.application.port.in.health;

import dev.sergevas.iot.env.domain.health.SystemInfo;

public interface SystemInfoUseCase {

    SystemInfo getSystemInfo();
}
