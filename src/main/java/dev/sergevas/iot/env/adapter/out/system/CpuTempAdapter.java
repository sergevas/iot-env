package dev.sergevas.iot.env.adapter.out.system;

import dev.sergevas.iot.env.application.port.out.SensorException;
import dev.sergevas.iot.env.application.port.out.health.CpuTempFetcher;
import dev.sergevas.iot.env.domain.SensorName;
import dev.sergevas.iot.env.domain.SensorType;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static dev.sergevas.iot.env.domain.ErrorEvent.E_SYSTEM_0001;

@ApplicationScoped
public class CpuTempAdapter implements CpuTempFetcher {

    public static final String[] FETCH_CPU_TEMP_CMD = new String[]{"cat", "/sys/class/thermal/thermal_zone0/temp"};
    private ProcessBuilder processBuilder;

    @PostConstruct
    public void init() {
        processBuilder = new ProcessBuilder().command(FETCH_CPU_TEMP_CMD);
    }

    @Loggable(logReturnVal = true)
    @Override
    public double getCpuTemp() {
        double cpuTemp;
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String cpuTempStr = reader.readLine();
            cpuTemp = Double.parseDouble(cpuTempStr) / 1000.0;
        } catch (Exception e) {
            throw new SensorException(E_SYSTEM_0001.getId(), SensorType.CPU_TEMP, SensorName.RASPBERRY_PI_ZERO_2, E_SYSTEM_0001.getName(), e);
        }
        return cpuTemp;
    }
}
