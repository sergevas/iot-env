package dev.sergevas.iot.env.system.boundary;

import dev.sergevas.iot.env.performance.control.Profiler;
import dev.sergevas.iot.env.shared.entity.SensorName;
import dev.sergevas.iot.env.shared.entity.SensorType;
import dev.sergevas.iot.env.shared.exception.SensorException;
import io.quarkus.logging.Log;
import jakarta.inject.Singleton;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static dev.sergevas.iot.env.shared.entity.ErrorEvent.E_SYSTEM_0001;

@Singleton
public class SystemInfoAdapter {

    private final CpuTempProcessBuilder cpuTempProcessBuilder;

    public SystemInfoAdapter(CpuTempProcessBuilder cpuTempProcessBuilder) {
        this.cpuTempProcessBuilder = cpuTempProcessBuilder;
    }

    public String getCpuTemp() {
        Profiler.init("SystemInfoAdapter.getCpuTemp()");
        String cpuTemp;
        try {
            Process process = cpuTempProcessBuilder.getProcessBuilder().start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Log.debug(Profiler.getCurrentMsg("SystemInfoAdapter.getCpuTemp()", "createBufferedReader"));
            String cpuTempStr = reader.readLine();
            cpuTemp = String.valueOf(Double.parseDouble(cpuTempStr) / 1000.0);
            Log.debug(Profiler.getCurrentMsg("SystemInfoAdapter.getCpuTemp()", "calcCpuTemp"));
        } catch (Exception e) {
            Log.error("Unable to get CPU Temp", e);
            throw new SensorException(E_SYSTEM_0001.getId(), SensorType.CPU_TEMP, SensorName.ORANGE_PI_ZERO, E_SYSTEM_0001.getName(), e);
        }
        Log.debug(Profiler.getCurrentMsg("SystemInfoAdapter.getCpuTemp()", "getCpuTempComplete"));
        return cpuTemp;
    }
}
