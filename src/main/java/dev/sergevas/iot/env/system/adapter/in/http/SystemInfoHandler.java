package dev.sergevas.iot.env.system.adapter.in.http;

import dev.sergevas.iot.env.EnvDeviceAppServiceManager;
import dev.sergevas.iot.env.shared.domain.SensorName;
import dev.sergevas.iot.env.shared.domain.SensorReadingsItemType;
import dev.sergevas.iot.env.shared.domain.SensorReadingsType;
import dev.sergevas.iot.env.shared.domain.SensorType;
import dev.sergevas.iot.env.system.application.port.in.SystemInfoUseCase;
import dev.sergevas.iot.env.system.domain.SystemInfo;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class SystemInfoHandler implements HttpHandler {
    private final SystemInfoUseCase systemInfoUseCase = EnvDeviceAppServiceManager.getInstance().getSystemInfoUseCase();

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        System.out.printf("Relative path: %s", exchange.getRelativePath()); // TODO: remove this
        SystemInfo systemInfo = systemInfoUseCase.getSystemInfo();
        SensorReadingsType sensorReadingsType = new SensorReadingsType()
                .addSReadingsItem(new SensorReadingsItemType()
                        .sType(SensorType.CPU_TEMP.name())
                        .sName(SensorName.ORANGE_PI_ZERO.getName())
                        .sTimestamp(OffsetDateTime.now(ZoneOffset.UTC))
                        .sData(String.valueOf(systemInfo.getCpuTemp())));
        System.out.printf("SensorReadingsType with SystemInfo data: %s", sensorReadingsType);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send(sensorReadingsType + "\n");
    }
}
