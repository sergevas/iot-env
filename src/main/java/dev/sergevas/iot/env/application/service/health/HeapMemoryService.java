package dev.sergevas.iot.env.application.service.health;

import dev.sergevas.iot.env.application.port.in.health.HeapMemoryUseCase;
import dev.sergevas.iot.env.application.port.out.health.HeapMemoryFetcher;
import dev.sergevas.iot.env.domain.health.HeapMemory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class HeapMemoryService implements HeapMemoryUseCase {

    @ConfigProperty(name = "healthcheck.heapMemory.thresholdPercent", defaultValue = "98")
    double heapMemoryThresholdPercent;
    @Inject
    HeapMemoryFetcher heapMemoryFetcher;

    @Override
    public HeapMemory getHeapMemory() {
        return heapMemoryFetcher.getHeapMemory();
    }

    public boolean isThresholdReached(HeapMemory heapMemory) {
        long threshold = (long) ((heapMemoryThresholdPercent / 100) * heapMemory.maxMemory());
        return threshold >= heapMemory.usedMemory();
    }
}
