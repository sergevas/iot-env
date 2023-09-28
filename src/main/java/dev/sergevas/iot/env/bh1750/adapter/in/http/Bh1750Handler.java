package dev.sergevas.iot.env.bh1750.adapter.in.http;

import dev.sergevas.iot.env.EnvDeviceAppServiceManager;
import dev.sergevas.iot.env.bh1750.application.port.in.Bh1750UseCase;
import dev.sergevas.iot.env.shared.domain.SensorName;
import dev.sergevas.iot.env.shared.domain.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.domain.SensorType;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Bh1750Handler implements HttpHandler {

    private final Bh1750UseCase bh1750UseCase = EnvDeviceAppServiceManager.getInstance().getBh1750UseCase();

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        SensorReadingsItemType sensorReadingsType = new SensorReadingsItemType()
                .sType(SensorType.LIGHT.name())
                .sName(SensorName.BH1750.getName())
                .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                .sData(String.valueOf(bh1750UseCase.getSensorReadingsItemTypeForBh1750().getLightIntensity()));
        System.out.printf("SensorReadingsType with Bh1750 data: %s", sensorReadingsType);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send(sensorReadingsType + "\n");
    }
}
