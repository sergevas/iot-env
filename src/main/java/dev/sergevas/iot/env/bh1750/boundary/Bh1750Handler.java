package dev.sergevas.iot.env.bh1750.boundary;

import dev.sergevas.iot.env.bh1750.control.Bh1750Service;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class Bh1750Handler {

    private final Bh1750Service bh1750Service;

    public Bh1750Handler(Bh1750Service bh1750Service) {
        this.bh1750Service = bh1750Service;
    }

    public Mono<ServerResponse> getSensorReadings(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bh1750Service.getSensorReadingsItemTypeForBh1750());
    }
}
