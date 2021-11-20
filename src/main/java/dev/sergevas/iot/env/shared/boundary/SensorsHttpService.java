package dev.sergevas.iot.env.shared.boundary;

import dev.sergevas.iot.env.bh1750.boundary.GetLightRequestHandler;
import dev.sergevas.iot.env.bme280.boundary.GetTHPRequestHandler;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.Service;

public class SensorsHttpService implements Service {

    private Config config;

    public SensorsHttpService(Config config) {
        super();
        this.config = config;
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/light", new GetLightRequestHandler(this.config));
        rules.get("/thp", new GetTHPRequestHandler(this.config));
    }
}
