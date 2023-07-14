package dev.sergevas.iot.env.system.boundary;

import dev.sergevas.iot.env.system.control.SystemInfoService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class SystemInfoHandler {

    private final SystemInfoService systemInfoService;

    public SystemInfoHandler(SystemInfoService systemInfoService) {
        this.systemInfoService = systemInfoService;
    }

    public Mono<ServerResponse> getSystemInfoReadings(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(systemInfoService.getSystemInfo());
    }
}
