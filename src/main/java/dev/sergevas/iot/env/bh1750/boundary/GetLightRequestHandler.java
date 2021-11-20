package dev.sergevas.iot.env.bh1750.boundary;

import dev.sergevas.iot.env.shared.controller.HelidonConfigHandler;
import dev.sergevas.iot.env.shared.model.SensorType;
import dev.sergevas.iot.env.shared.controller.SensorResponseBuilder;
import io.helidon.config.Config;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

import javax.json.JsonObject;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


public class GetLightRequestHandler implements Handler {

    private Bh1750Adapter bh1750Adapter;

    public GetLightRequestHandler(Config config) {
        bh1750Adapter = Bh1750Adapter.create(HelidonConfigHandler.getConfigMap(config, "bh1750"));
    }

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        JsonObject returnObject = new SensorResponseBuilder()
                .item(new SensorResponseBuilder.Item()
                .sType(SensorType.LIGHT)
                .sData(bh1750Adapter.getLightIntensity())
                .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC)))
                .buildSensorReadingsItem();
        res.send(returnObject);

    }
}
