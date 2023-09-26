package dev.sergevas.iot.env;


import dev.sergevas.iot.env.bh1750.adapter.in.http.Bh1750Handler;
import dev.sergevas.iot.env.system.adapter.in.http.SystemInfoHandler;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;

import java.time.LocalDateTime;

//@ApplicationPath("/iotenv/api/v1")
public class EnvDeviceApp {

    public static void main(String[] args) {
        EnvDeviceAppConfig config = EnvDeviceAppServiceManager.getInstance().getEnvDeviceAppConfig();
        final HttpHandler routes = new RoutingHandler()
                .get("/bh1750", new Bh1750Handler())
                .get("/system", new SystemInfoHandler());
        Undertow server = Undertow.builder()
                .addHttpListener(config.getHttpPort(), "0.0.0.0")
                .setHandler(routes)
                .build();
        System.out.println(LocalDateTime.now() + " Start server...");
        server.start();
        System.out.println(LocalDateTime.now() + " Start server complete...");
    }
}
