package dev.sergevas.iot.env.system.boundary;

import dev.sergevas.iot.env.performance.control.Profiler;
import dev.sergevas.iot.env.shared.entity.SensorType;
import dev.sergevas.iot.env.shared.exception.SensorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static dev.sergevas.iot.env.shared.entity.ErrorEventId.E_SYSTEM_0001;

public class SystemInfoAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(SystemInfoAdapter.class);

    public static final String[] FETCH_CPU_TEMP_CMD = new String[]{"cat", "/sys/class/thermal/thermal_zone0/temp"};
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
            LOG.debug(Profiler.getCurrentMsg("SystemInfoAdapter.getCpuTemp()", "createBufferedReader"));
            String cpuTempStr = reader.readLine();
            cpuTemp = String.valueOf(Double.parseDouble(cpuTempStr) / 1000.0);
            LOG.debug(Profiler.getCurrentMsg("SystemInfoAdapter.getCpuTemp()", "calcCpuTemp"));
        } catch (Exception e) {
            LOG.error("Unable to get CPU Temp", e);
            throw new SensorException(E_SYSTEM_0001.getId(), SensorType.CPU_TEMP, E_SYSTEM_0001.getName(), e);
        }
        LOG.debug(Profiler.getCurrentMsg("SystemInfoAdapter.getCpuTemp()", "getCpuTempComplete"));
        return cpuTemp;
    }
}
