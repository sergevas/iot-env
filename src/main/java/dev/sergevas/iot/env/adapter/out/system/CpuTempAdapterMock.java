package dev.sergevas.iot.env.adapter.out.system;

import dev.sergevas.iot.env.application.port.out.CpuTemp;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
public class CpuTempAdapterMock implements CpuTemp {

    @Override
    public double getCpuTemp() {
        return 41.53;
    }
}
