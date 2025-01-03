package dev.sergevas.iot.env.adapter.out.system;

import dev.sergevas.iot.env.application.port.out.health.HeapMemoryFetcher;
import dev.sergevas.iot.env.domain.health.HeapMemory;
import dev.sergevas.iot.env.infra.log.interceptor.Loggable;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Some chunks of the code is from
 * io\helidon\health\helidon-health-checks\2.3.2\helidon-health-checks-2.3.2-sources.jar!\io\helidon\health\checks\HeapMemoryHealthCheck.java
 */

@ApplicationScoped
public class HeapMemoryAdapter implements HeapMemoryFetcher {

    private Runtime rt;

    @PostConstruct
    public void init() {
        rt = Runtime.getRuntime();
    }

    @Loggable(logReturnVal = true)
    @Override
    public HeapMemory getHeapMemory() {
        return new HeapMemory(rt.freeMemory(), rt.totalMemory(), rt.maxMemory());
    }
}
