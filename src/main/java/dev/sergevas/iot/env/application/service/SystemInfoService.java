package dev.sergevas.iot.env.application.service;

import dev.sergevas.iot.env.application.port.in.SystemInfoUseCase;
import dev.sergevas.iot.env.application.port.out.CpuTemp;
import dev.sergevas.iot.env.domain.SystemInfo;

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
