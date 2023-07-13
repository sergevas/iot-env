package dev.sergevas.iot.env.system.boundary;

public class CpuTempProcessBuilder {

    public static final String[] FETCH_CPU_TEMP_CMD = new String[]{"cat", "/sys/class/thermal/thermal_zone0/temp"};
    private final ProcessBuilder processBuilder;

    public CpuTempProcessBuilder() {
        this.processBuilder = new ProcessBuilder().command(FETCH_CPU_TEMP_CMD);
    }

    public ProcessBuilder getProcessBuilder() {
        return processBuilder;
    }
}
