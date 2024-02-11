package dev.sergevas.iot.env.application.port.out.health;


import dev.sergevas.iot.env.domain.health.HeapMemory;

public interface HeapMemoryFetcher {

    HeapMemory getHeapMemory();
}
