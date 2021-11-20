package dev.sergevas.iot.env.bme280.boundary;

import dev.sergevas.iot.env.bme280.model.Bme280Readings;
import dev.sergevas.iot.env.shared.controller.HelidonConfigHandler;
import dev.sergevas.iot.env.shared.controller.SensorResponseBuilder;
import dev.sergevas.iot.env.shared.model.SensorType;
import io.helidon.config.Config;
import io.helidon.webserver.Handler;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;

import javax.json.JsonObject;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


public class GetTHPRequestHandler implements Handler {

    private Bmep280Adapter bmep280Adapter;

    public GetTHPRequestHandler(Config config) {
        bmep280Adapter = Bmep280Adapter.create(HelidonConfigHandler.getConfigMap(config, "bmep280"));
    }

    @Override
    public void accept(ServerRequest req, ServerResponse res) {
        Bme280Readings readings = this.bmep280Adapter.getThpReadings();
        OffsetDateTime sTimestamp = OffsetDateTime.now(ZoneOffset.UTC);
        JsonObject returnObject = new SensorResponseBuilder()
                .item(new SensorResponseBuilder.Item()
                        .sId(readings.getId())
                        .sType(SensorType.TEMP)
                        .sData(readings.getTemperature())
                        .sTimestamp(sTimestamp))
                .item(new SensorResponseBuilder.Item()
                        .sId(readings.getId())
                        .sType(SensorType.HUMID)
                        .sData(readings.getHumidity())
                        .sTimestamp(sTimestamp))
                .item(new SensorResponseBuilder.Item()
                        .sId(readings.getId())
                        .sType(SensorType.PRESS)
                        .sData(readings.getPressure())
                        .sTimestamp(sTimestamp))
                .buildSensorReadings();
        res.send(returnObject);
    }
}
