package dev.sergevas.iot.env.application.service.health;

import dev.sergevas.iot.env.application.port.in.health.SystemInfoUseCase;
import dev.sergevas.iot.env.application.port.out.health.CpuTempFetcher;
import dev.sergevas.iot.env.domain.health.SystemInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SystemInfoService implements SystemInfoUseCase {
    private final CpuTempFetcher cpuTemp;

    @Inject
    public SystemInfoService(CpuTempFetcher cpuTEmp) {
        this.cpuTemp = cpuTEmp;
    }

    @Override
    public SystemInfo getSystemInfo() {
        return new SystemInfo().cpuTemp(cpuTemp.getCpuTemp());
    }
}
