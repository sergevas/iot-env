package dev.sergevas.iot.env.system.application.service;

import dev.sergevas.iot.env.system.application.port.in.SystemInfoUseCase;
import dev.sergevas.iot.env.system.application.port.out.CpuTemp;
import dev.sergevas.iot.env.system.domain.SystemInfo;

public class SystemInfoService implements SystemInfoUseCase {
    private final CpuTemp cpuTemp;

    public SystemInfoService(CpuTemp cpuTEmp) {
        this.cpuTemp = cpuTEmp;
    }

    @Override
    public SystemInfo getSystemInfo() {
        return new SystemInfo().cpuTemp(cpuTemp.getCpuTemp());
    }
}
