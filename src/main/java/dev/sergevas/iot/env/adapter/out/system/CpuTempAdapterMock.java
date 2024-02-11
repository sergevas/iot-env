package dev.sergevas.iot.env.adapter.out.system;

import dev.sergevas.iot.env.application.port.out.health.CpuTempFetcher;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultBean
public class CpuTempAdapterMock implements CpuTempFetcher {

    @Override
    public double getCpuTemp() {
        return 41.53;
    }
}
