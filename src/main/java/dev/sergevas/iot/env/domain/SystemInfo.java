package dev.sergevas.iot.env.domain;

import java.util.Objects;
import java.util.StringJoiner;

public class SystemInfo {
    private double cpuTemp;

    public double getCpuTemp() {
        return cpuTemp;
    }

    public SystemInfo cpuTemp(double cpuTemp) {
        this.cpuTemp = cpuTemp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemInfo that = (SystemInfo) o;
        return Double.compare(that.cpuTemp, cpuTemp) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpuTemp);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SystemInfo.class.getSimpleName() + "[", "]")
                .add("cpuTemp=" + cpuTemp)
                .toString();
    }
}
