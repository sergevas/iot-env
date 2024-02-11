package dev.sergevas.iot.env.application.port.in.health;

import dev.sergevas.iot.env.domain.health.HeapMemory;

public interface HeapMemoryUseCase {

    HeapMemory getHeapMemory();

    boolean isThresholdReached(HeapMemory heapMemory);
}
