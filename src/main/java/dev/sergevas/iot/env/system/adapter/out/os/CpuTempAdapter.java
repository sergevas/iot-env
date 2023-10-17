package dev.sergevas.iot.env.system.adapter.out.os;

import dev.sergevas.iot.env.performance.Profiler;
import dev.sergevas.iot.env.shared.application.port.out.SensorException;
import dev.sergevas.iot.env.shared.domain.SensorName;
import dev.sergevas.iot.env.shared.domain.SensorType;
import dev.sergevas.iot.env.system.application.port.out.CpuTemp;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static dev.sergevas.iot.env.shared.domain.ErrorEvent.E_SYSTEM_0001;

@ApplicationScoped
public class CpuTempAdapter implements CpuTemp {

    public static final String[] FETCH_CPU_TEMP_CMD = new String[]{"cat", "/sys/class/thermal/thermal_zone0/temp"};
    private final ProcessBuilder processBuilder;

    public CpuTempAdapter() {
        this.processBuilder = new ProcessBuilder().command(FETCH_CPU_TEMP_CMD);
    }

    public double getCpuTemp() {
        Profiler.init("CpuTempAdapter.getCpuTemp()");
        double cpuTemp;
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Log.info(Profiler.getCurrentMsg("CpuTempAdapter.getCpuTemp()", "createBufferedReader"));
            String cpuTempStr = reader.readLine();
            cpuTemp = Double.parseDouble(cpuTempStr) / 1000.0;
            Log.info(Profiler.getCurrentMsg("CpuTempAdapter.getCpuTemp()", "calcCpuTemp"));
        } catch (Exception e) {
            Log.error("Unable to get CPU Temp ", e);
            throw new SensorException(E_SYSTEM_0001.getId(), SensorType.CPU_TEMP, SensorName.ORANGE_PI_ZERO, E_SYSTEM_0001.getName(), e);
        }
        Log.info(Profiler.getCurrentMsg("CpuTempAdapter.getCpuTemp()", "getCpuTempComplete"));
        return cpuTemp;
    }
}
